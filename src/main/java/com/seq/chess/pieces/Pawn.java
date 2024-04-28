package main.java.com.seq.chess.pieces;

import main.java.com.seq.chess.Board;
import main.java.com.seq.chess.BoardPos;
import main.java.com.seq.chess.Piece;
import main.java.com.seq.chess.Team;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(BoardPos pos, Team team) {
        super(pos, team, "pawn");
    }

    @Override
    public List<BoardPos> getPossiblePositions() {
        ArrayList<BoardPos> possiblePositions = new ArrayList<>();

        Piece rightPiece;
        Piece leftPiece;

        // For Black
        if (this.team == Team.BLACK) {
            BoardPos frontPos = new BoardPos(this.pos.row + 1, this.pos.column);
            if (Board.getPieceAt(frontPos) == null) {
                possiblePositions.add(frontPos);
            }

            // Double move Pawn on first move
            BoardPos doublePos = new BoardPos(this.pos.row + 2, this.pos.column);
            if (Board.getPieceAt(doublePos) == null && this.pos.row == 1) {
                possiblePositions.add(doublePos);
            }

            rightPiece = Board.getPieceAt(new BoardPos(this.pos.row + 1, this.pos.column + 1));
            leftPiece = Board.getPieceAt(new BoardPos(this.pos.row + 1, this.pos.column - 1));

        // For White
        } else {
            BoardPos frontPos = new BoardPos(this.pos.row - 1, this.pos.column);
            if (Board.getPieceAt(frontPos) == null) {
                possiblePositions.add(frontPos);
            }

            // Double move Pawn on first move
            BoardPos doublePos = new BoardPos(this.pos.row - 2, this.pos.column);
            if (Board.getPieceAt(doublePos) == null && this.pos.row == 6) {
                possiblePositions.add(doublePos);
            }

            rightPiece = Board.getPieceAt(new BoardPos(this.pos.row - 1, this.pos.column + 1));
            leftPiece = Board.getPieceAt(new BoardPos(this.pos.row - 1, this.pos.column - 1));
        }



        if (rightPiece != null && rightPiece.team != this.team) {
            possiblePositions.add(rightPiece.pos);
        }

        if (leftPiece != null && leftPiece.team != this.team) {
            possiblePositions.add(leftPiece.pos);
        }

        return possiblePositions;
    }
}
