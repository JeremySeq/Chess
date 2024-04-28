package main.java.com.seq.chess.pieces;

import main.java.com.seq.chess.Board;
import main.java.com.seq.chess.BoardPos;
import main.java.com.seq.chess.Piece;
import main.java.com.seq.chess.Team;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(BoardPos pos, Team team) {
        super(pos, team, "knight");
    }

    @Override
    public List<BoardPos> getPossiblePositions() {
        ArrayList<BoardPos> possiblePositions = new ArrayList<>();

        ArrayList<BoardPos> checkPositions = new ArrayList<>();
        checkPositions.add(new BoardPos(this.pos.row + 2, this.pos.column + 1));
        checkPositions.add(new BoardPos(this.pos.row + 1, this.pos.column + 2));
        checkPositions.add(new BoardPos(this.pos.row - 2, this.pos.column - 1));
        checkPositions.add(new BoardPos(this.pos.row - 1, this.pos.column - 2));
        checkPositions.add(new BoardPos(this.pos.row - 2, this.pos.column + 1));
        checkPositions.add(new BoardPos(this.pos.row + 2, this.pos.column - 1));
        checkPositions.add(new BoardPos(this.pos.row - 1, this.pos.column + 2));
        checkPositions.add(new BoardPos(this.pos.row + 1, this.pos.column - 2));

        for (BoardPos x : checkPositions) {
            Piece piece = Board.getPieceAt(x);
            if (!(piece != null && piece.team == this.team)) {
                possiblePositions.add(x);
            }
        }

        return possiblePositions;
    }
}
