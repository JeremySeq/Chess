package main.java.com.seq.chess;

public class BoardPos {

    public int row;
    public int column;

    public BoardPos(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof BoardPos boardPos) {
            return boardPos.row == this.row && boardPos.column == this.column;
        }

        return super.equals(obj);
    }
}
