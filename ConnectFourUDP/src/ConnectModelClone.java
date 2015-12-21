import java.io.IOException;

/**
 * Created by michael on 7/12/2015.
 */
public class ConnectModelClone implements ModelListener {
    private int NUMHEAPS = 7;
    private int NUMOBJECTS = 6;
    private ModelListener modelListener;
    private ConnectBoard board = new ConnectBoard();

    public ConnectModelClone(){}

    public ConnectBoard getBoard(){
        return board;
    }

    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
    }

    @Override
    public void id(int id) throws IOException {}

    @Override
    public void name(int id, String name) throws IOException {}

    @Override
    public void score(int id, int score) throws IOException {}

    @Override
    public void reset() throws IOException {
        Piece p = new Piece(5);
        for (int i = 0; i <NUMHEAPS; i++) {
            for (int j = 0; j < NUMOBJECTS ; j++) {
                board.setSpot(i, j, p);
            }
        }
    }

    @Override
    public void move(int id, int x, int y) throws IOException {
        System.out.println("IM IN THE CONNECTMODELCLONE");
        System.out.println("This is the id: "+ id);
        Piece p = new Piece(id);
        board.setSpot(x, y, p);
        modelListener.move(id,x,y);
    }

    @Override
    public void turn(int id) throws IOException {}

    @Override
    public void win(int id) throws IOException {}

    @Override
    public void quit() throws IOException {}
}
