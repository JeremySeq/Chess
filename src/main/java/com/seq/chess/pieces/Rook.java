package main.java.com.seq.chess.pieces;

import main.java.com.seq.chess.Board;
import main.java.com.seq.chess.BoardPos;
import main.java.com.seq.chess.Piece;
import main.java.com.seq.chess.Team;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(BoardPos pos, Team team) {
        super(pos, team, "rook");
    }

    @Override
    public List<BoardPos> getPossiblePositions() {
        ArrayList<BoardPos> possiblePositions = new ArrayList<>();

        // move up
        for (int i = this.pos.row-1; i >= 0; i--) {


            BoardPos currentPos = new BoardPos(i, this.pos.column);

            Piece piece = Board.getPieceAt(currentPos);

            // if hit team's piece stop
            if (piece != null && piece.team == this.team) {
                break;
            }
            // if hit enemy piece, count that, then stop
            else if (piece != null) {
                possiblePositions.add(currentPos);
                break;
            }

            possiblePositions.add(currentPos);

        }

        // move down
        for (int i = this.pos.row+1; i < 8; i++) {

            BoardPos currentPos = new BoardPos(i, this.pos.column);

            Piece piece = Board.getPieceAt(currentPos);

            // if hit team's piece stop
            if (piece != null && piece.team == this.team) {
                break;
            }
            // if hit enemy piece, count that, then stop
            else if (piece != null) {
                possiblePositions.add(currentPos);
                break;
            }

            possiblePositions.add(currentPos);

        }

        // move right
        for (int i = this.pos.column+1; i < 8; i++) {

            BoardPos currentPos = new BoardPos(this.pos.row, i);

            Piece piece = Board.getPieceAt(currentPos);

            // if hit team's piece stop
            if (piece != null && piece.team == this.team) {
                break;
            }
            // if hit enemy piece, count that, then stop
            else if (piece != null) {
                possiblePositions.add(currentPos);
                break;
            }

            possiblePositions.add(currentPos);

        }

        // move left
        for (int i = this.pos.column-1; i >= 0; i--) {

            BoardPos currentPos = new BoardPos(this.pos.row, i);

            Piece piece = Board.getPieceAt(currentPos);

            // if hit team's piece stop
            if (piece != null && piece.team == this.team) {
                break;
            }
            // if hit enemy piece, count that, then stop
            else if (piece != null) {
                possiblePositions.add(currentPos);
                break;
            }

            possiblePositions.add(currentPos);

        }

        return possiblePositions;
    }
}
