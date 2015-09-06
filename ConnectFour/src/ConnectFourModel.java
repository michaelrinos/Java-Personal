import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by michael on 5/9/2015.
 */
public class ConnectFourModel extends Observable {

    // The default size of the board width
    public static final int BOARD_WIDTH = 7;

    //The default size of the board height
    public static final int BOARD_LENGTH = 6;

    //The board
    private ArrayList<Piece> Board;

    //Array containing all the pieces player 1 has
    private ArrayList<Piece> Player1Pieces;

    //Array containing all the pieces player 2 has
    private ArrayList<Piece> Player2Pieces;

    //total number of moves made in the game
    private int movecount;
    

}
