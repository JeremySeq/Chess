package main.java.com.seq.chess;

import main.java.com.seq.chess.pieces.King;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    public Team team;

    public BoardPos pos;

    private BufferedImage image;

    private final String name;

    public static final Color POSSIBLE_POSITION_COLOR = new Color(119, 119, 131, 150);


    public Piece(BoardPos pos, Team team, String name) {
        this.pos = pos;
        this.team = team;
        this.name = name;

        loadImage();
    }

    public void loadImage() {
        try {
            image = ImageIO.read(new File("src/main/resources/" + this.team.toString().toLowerCase() + "/" + name + ".png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }


    public List<BoardPos> getPossiblePositions() {
        return new ArrayList<>();
    }

    public void draw(Graphics g, ImageObserver observer, boolean flipped) {
        int drawX = pos.column;
        int drawY = pos.row;
        if (flipped) {
            drawX = 7 - drawX;
            drawY = 7 - drawY;
        }
        g.drawImage(
                image.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, 1),
                drawX * Board.TILE_SIZE,
                drawY * Board.TILE_SIZE,
                observer
        );

    }

    public void drawImageOverlay(BufferedImage image, Graphics g, ImageObserver observer, boolean flipped) {
        int drawX = this.pos.column;
        int drawY = this.pos.row;
        if (flipped) {
            drawX = 7 - drawX;
            drawY = 7 - drawY;
        }

        g.drawImage(
                image.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, 1),
                drawX * Board.TILE_SIZE,
                drawY * Board.TILE_SIZE,
                observer
        );
    }

    public void drawPossiblePositions(Graphics g, boolean flipped) {
        List<BoardPos> possiblePositions = this.getPossiblePositions();
        for (BoardPos pos : possiblePositions) {
            g.setColor(POSSIBLE_POSITION_COLOR);

            int column = pos.column;
            int row = pos.row;
            if (flipped) {
                column = 7 - column;
                row = 7 - row;
            }

            g.fillOval(column * Board.TILE_SIZE + Board.TILE_SIZE / 3,
                    row * Board.TILE_SIZE + Board.TILE_SIZE / 3,
                    Board.TILE_SIZE / 3,
                    Board.TILE_SIZE / 3);
        }
    }

    public boolean isMovePossible(BoardPos targetPos) {
        List<BoardPos> possiblePositions = this.getPossiblePositions();

        Piece pieceToAdd = null;

        Piece piece = Board.getPieceAt(targetPos);
        if (piece != null && piece.team != Board.playing_team) {
            Board.pieces.remove(piece);
            pieceToAdd = piece;
        }

        BoardPos prevPos = this.pos;
        this.pos = targetPos;



        if (King.isSafeMove(this.team, Board.pieces)) {
            this.pos = prevPos;

            if (pieceToAdd != null) {
                Board.pieces.add(pieceToAdd);
            }

            return possiblePositions.contains(targetPos);
        } else {
            this.pos = prevPos;

            if (pieceToAdd != null) {
                Board.pieces.add(pieceToAdd);
            }

            return false;
        }
    }

    public void moveTo(BoardPos targetPos) {
        if (isMovePossible(targetPos)) {

            Piece piece = Board.getPieceAt(targetPos);
            if (piece != null && piece.team != Board.playing_team) {
                Board.pieces.remove(piece);
            }

            this.pos = targetPos;
        }
    }
}
