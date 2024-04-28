package main.java.com.seq.chess;

import main.java.com.seq.chess.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener, KeyListener {

    // controls the delay between each tick in ms
    private final int DELAY = 25;
    // controls the size of the board
    public static final int TILE_SIZE = 80;
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    public static final Color BOARD_COLOR_1 = new Color(234,233,210);

    public static final Color BOARD_COLOR_2 = new Color(75,115,153);

    public static ArrayList<Piece> pieces = new ArrayList<>();

    public static Team playing_team = Team.WHITE;

    public static Piece selectedPiece = null;

    public static boolean flipped = false;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;

    public static BufferedImage selectImage;

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color

        setBackground(BOARD_COLOR_1);

        try {
            selectImage = ImageIO.read(new File("src/main/resources/select.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }


        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();



        // mouse events
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX()/TILE_SIZE;
                int row = e.getY()/TILE_SIZE;

                if (flipped) {
                    col = 7 - col;
                    row = 7 - row;
                }

                Piece clickedPiece = getPieceAt(new BoardPos(row, col));


                // if player clicks on the selected piece, deselect it
                if (clickedPiece != null && clickedPiece == selectedPiece) {
                    selectedPiece = null;
                }
                // select piece if player clicked on it and it is the playing team's piece
                else if (clickedPiece != null && clickedPiece.team == playing_team) {
                    selectedPiece = clickedPiece;
                }


                // if piece is selected and player clicks on enemy piece or empty square move the selected piece
                if (selectedPiece != null && (clickedPiece == null || clickedPiece.team != playing_team)) {
                    // move to square if possible, then deselect
                    if (selectedPiece.isMovePossible(new BoardPos(row, col))) {

                        // MAKE MOVE
                        selectedPiece.moveTo(new BoardPos(row, col));
                        selectedPiece = null;


                        if (playing_team == Team.WHITE) {
                            playing_team = Team.BLACK;
                        } else {
                            playing_team = Team.WHITE;
                        }
                        flipBoard();

                    }
                    // deselect piece if clicked on place not able to move to
                    else {
                        Board.selectedPiece = null;
                    }

                }
            }
        });

        setupBoard();

    }

    public static void flipBoard() {
        flipped = !flipped;
    }

    public static void setupBoard() {
        pieces.clear();

        pieces.add(new Rook(new BoardPos(0, 0), Team.BLACK));
        pieces.add(new Rook(new BoardPos(0, 7), Team.BLACK));
        pieces.add(new Rook(new BoardPos(7, 0), Team.WHITE));
        pieces.add(new Rook(new BoardPos(7, 7), Team.WHITE));

        pieces.add(new Bishop(new BoardPos(0, 2), Team.BLACK));
        pieces.add(new Bishop(new BoardPos(0, 5), Team.BLACK));
        pieces.add(new Bishop(new BoardPos(7, 2), Team.WHITE));
        pieces.add(new Bishop(new BoardPos(7, 5), Team.WHITE));

        pieces.add(new Knight(new BoardPos(0, 1), Team.BLACK));
        pieces.add(new Knight(new BoardPos(0, 6), Team.BLACK));
        pieces.add(new Knight(new BoardPos(7, 1), Team.WHITE));
        pieces.add(new Knight(new BoardPos(7, 6), Team.WHITE));

        pieces.add(new King(new BoardPos(0, 4), Team.BLACK));
        pieces.add(new King(new BoardPos(7, 4), Team.WHITE));

        pieces.add(new Queen(new BoardPos(0, 3), Team.BLACK));
        pieces.add(new Queen(new BoardPos(7, 3), Team.WHITE));

        pieces.add(new Pawn(new BoardPos(6, 0), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 1), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 2), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 3), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 4), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 5), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 6), Team.WHITE));
        pieces.add(new Pawn(new BoardPos(6, 7), Team.WHITE));

        pieces.add(new Pawn(new BoardPos(1, 0), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 1), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 2), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 3), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 4), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 5), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 6), Team.BLACK));
        pieces.add(new Pawn(new BoardPos(1, 7), Team.BLACK));
    }

    public static Piece getPieceAt(BoardPos pos) {
        for (Piece piece : pieces) {
            if (piece.pos.equals(pos)) {
                return piece;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" main.java.com.seq.chess.Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        drawBackground(g);


        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(BOARD_COLOR_2);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position


                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );
                }
            }
        }

        for (Piece piece : pieces) {
            piece.draw(g, this, flipped);
        }

        // draw select overlay on selected piece and possible positions
        if (selectedPiece != null) {

            int drawX = selectedPiece.pos.column;
            int drawY = selectedPiece.pos.row;
            if (flipped) {
                drawX = 7 - drawX;
                drawY = 7 - drawY;
            }

            g.drawImage(
                    selectImage.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, 1),
                    drawX * Board.TILE_SIZE,
                    drawY * Board.TILE_SIZE,
                    this
            );

            selectedPiece.drawPossiblePositions(g, flipped);
        }

        if (King.isChecked(playing_team)) {

            King king = King.getTeamKing(playing_team);

            int drawX = king.pos.column;
            int drawY = king.pos.row;
            if (flipped) {
                drawX = 7 - drawX;
                drawY = 7 - drawY;
            }

            g.setColor(new Color(255, 60, 60, 150));

            g.fillRect(
                    drawX * TILE_SIZE,
                    drawY * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE
            );
        }

        g.setColor(new Color(0, 0, 0, 100));
        String s;
        if (playing_team == Team.WHITE) {
            s = "WHITE TURN";
        } else {
            s = "BLACK TURN";
        }
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(s, 10, 40);
    }

}