/**
 * Created by michael on 6/11/2015.
 */
public class ConnectBoard {
    public static final int COLS = 6;
    public static final int ROWS = 7;
    public static final int BOARD_COLS = 6;
    public static final int BOARD_ROWS = 7;
    private static final int BoardID = 5;
    private Piece[][] Board;
    private int moveCount= 0;


    public ConnectBoard(){
        Board = new Piece[BOARD_ROWS][BOARD_COLS];
        clearBoard();
    }

    public synchronized void clearBoard(){
        for (int i = BOARD_ROWS-1; i >= 0; i--) {
            for (int j = BOARD_COLS-1; j >= 0; j--) {
                Board[i][j] = new Piece(BoardID);
            }
        }
    }


    public synchronized boolean setSpot(int x, int y, Piece p){
        if (Board[x][y].getOwner() ==5){
            Board[x][y] = p;
            return true;
        }
        else {
            return false;
        }
    }

    public synchronized Piece getSpot(int x, int y){
        if (x <0 || y < 0 || x > ROWS || y > COLS)
            return null;
        return Board[x][y];
    }

    public synchronized Piece[][] getBoard(){
        return this.Board;
    }

    public synchronized int getNextY(int x) {

        for (int i = COLS - 1; i >= 0; i--) {
            if (Board[i][x].getOwner() != 5) {
                return i + 1;
            }
        }
        if (Board[x][0].getOwner() == 5) {
            return 0;
        } else return -1;
    }
}
