import java.io.IOException;

/**
 * Created by michael on 6/11/2015.
 */
public interface ModelListener {


    public void id(int id) throws IOException;

    /**
     * Sent to each client to report one of the player's names.
     * <i> is replaced with the ID of the player whose name is being reported.
     * <n> is replaced with that player's name.
     * @param id the players id
     * @param name the players name
     * @throws IOException
     */
    public void name(int id, String name) throws IOException;

    public void score(int id, int score) throws IOException;

    public void reset() throws IOException;

    public void move(int id, int x, int y) throws IOException;

    public void turn(int id) throws IOException;

    public void win(int id) throws IOException;

    public void quit() throws IOException;
}
