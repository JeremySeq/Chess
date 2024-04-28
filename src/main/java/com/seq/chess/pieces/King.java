package main.java.com.seq.chess.pieces;

import main.java.com.seq.chess.Board;
import main.java.com.seq.chess.BoardPos;
import main.java.com.seq.chess.Piece;
import main.java.com.seq.chess.Team;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(BoardPos pos, Team team) {
        super(pos, team, "king");
    }

    @Override
    public List<BoardPos> getPossiblePositions() {
        ArrayList<BoardPos> possiblePositions = new ArrayList<>();

        ArrayList<BoardPos> checkPositions = new ArrayList<>();
        checkPositions.add(new BoardPos(this.pos.row, this.pos.column - 1));
        checkPositions.add(new BoardPos(this.pos.row, this.pos.column + 1));
        checkPositions.add(new BoardPos(this.pos.row - 1, this.pos.column));
        checkPositions.add(new BoardPos(this.pos.row + 1, this.pos.column));
        checkPositions.add(new BoardPos(this.pos.row - 1, this.pos.column + 1));
        checkPositions.add(new BoardPos(this.pos.row + 1, this.pos.column - 1));
        checkPositions.add(new BoardPos(this.pos.row - 1, this.pos.column - 1));
        checkPositions.add(new BoardPos(this.pos.row + 1, this.pos.column + 1));

        for (BoardPos x : checkPositions) {
            Piece piece = Board.getPieceAt(x);
            if (!(piece != null && piece.team == this.team)) {
                possiblePositions.add(x);
            }
        }

        return possiblePositions;
    }


    /**
     * used by other pieces to make sure the move does not leave the king in check
     */
    public static boolean isSafeMove(Team team, List<Piece> stateOfBoardAfter) {


        // find team's king
        King king = getTeamKing(team);


        for (Piece piece : stateOfBoardAfter) {
            if (piece.getPossiblePositions().contains(king.pos)) {
                return false;
            }
        }


        return true;
    }

    public static boolean isChecked(Team team) {
        // find team's king
        King king = getTeamKing(team);


        for (Piece piece : Board.pieces) {
            if (piece.isMovePossible(king.pos)) {
                return true;
            }
        }

        return false;
    }


    public static King getTeamKing(Team team) {
        King king = null;
        for (Piece piece : Board.pieces) {
            if (piece instanceof King kingPiece && kingPiece.team == team) {
                king = (King) piece;
                break;
            }
        }
        assert king != null;

        return king;
    }

}
