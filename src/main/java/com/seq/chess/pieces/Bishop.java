package main.java.com.seq.chess.pieces;

import main.java.com.seq.chess.Board;
import main.java.com.seq.chess.BoardPos;
import main.java.com.seq.chess.Piece;
import main.java.com.seq.chess.Team;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {


    public Bishop(BoardPos pos, Team team) {
        super(pos, team, "bishop");
    }

    @Override
    public List<BoardPos> getPossiblePositions() {

        ArrayList<BoardPos> possiblePositions = new ArrayList<>();

        // move down right
        int i = this.pos.row + 1;
        int j = this.pos.column + 1;
        while (i < 8 && j < 8) {
            BoardPos currentPos = new BoardPos(i, j);

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

            i++;
            j++;
        }

        // move down left
        i = this.pos.row + 1;
        j = this.pos.column - 1;
        while (i < 8 && j >= 0) {
            BoardPos currentPos = new BoardPos(i, j);

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

            i++;
            j--;
        }


        // move up left
        i = this.pos.row - 1;
        j = this.pos.column - 1;
        while (i >= 0 && j >= 0) {
            BoardPos currentPos = new BoardPos(i, j);

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

            i--;
            j--;
        }

        // move up right
        i = this.pos.row - 1;
        j = this.pos.column + 1;
        while (i >= 0 && j < 8) {
            BoardPos currentPos = new BoardPos(i, j);

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

            i--;
            j++;
        }

        return possiblePositions;

    }
}
