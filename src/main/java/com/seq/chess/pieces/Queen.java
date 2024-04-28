package main.java.com.seq.chess.pieces;

import main.java.com.seq.chess.BoardPos;
import main.java.com.seq.chess.Piece;
import main.java.com.seq.chess.Team;

import java.util.List;

public class Queen extends Piece {
    public Queen(BoardPos pos, Team team) {
        super(pos, team, "queen");
    }

    @Override
    public List<BoardPos> getPossiblePositions() {
        List<BoardPos> possiblePositionsRook = new Rook(this.pos, this.team).getPossiblePositions();
        List<BoardPos> possiblePositionsBishop = new Bishop(this.pos, this.team).getPossiblePositions();

        List<BoardPos> possiblePositions = possiblePositionsRook;
        possiblePositions.addAll(possiblePositionsBishop);

        return possiblePositions;
    }
}
