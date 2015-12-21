import java.util.ArrayList;

/**
 * Class Player contains references to ids model score and name of each player in the nim game
 *
 * @author Michael Rinos
 */
public class Player {
    // Hidden data members.
    private int id;
    private int score;
    private String name;
    private ConnectBoard board;
    private ModelListener modelListener;

    // Exported constructors.

    /**
     * Constructs a new view player
     * @param id player id
     * @param name player name
     * @param modelListener model reference for the game
     */
    public Player(int id, String name, ModelListener modelListener ){
        this.id=id;
        this.score=0;
        this.name = name;
        this.board = new ConnectBoard();
        this.modelListener= modelListener;
    }

    //Getters and setters for most private data
    public synchronized ModelListener getModelListener() {
        return modelListener;
    }

    public synchronized int getScore() {
        return score;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized int getId() {
        return id;
    }

    /**
     *  increases the players score
     */
    public synchronized void incScore() {
        this.score ++;
    }

    public synchronized void move(int x, int y, Piece p){board.setSpot(x,y,p);}

    public synchronized void clearBoard(){
        board.clearBoard();
    }

    public synchronized Piece getSpot(int x, int y){
        return board.getSpot(x,y);
    }

}
