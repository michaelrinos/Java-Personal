import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class SessionManager maintains the sessions' model objects in the Network Go
 * Game server.
 *
 * @author  Alan Kaminsky
 * @version 21-Jan-2010
 */
public class SessionManager
        implements ViewListener
{

    // Hidden data members.
    private boolean twoPlayers = false;
    private ConnectModel model;

// Exported constructors.

    /**
     * Construct a new session manager.
     */
    public SessionManager() {}

// Exported operations.

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  session  Session name.
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public synchronized void join(ViewProxy proxy, String session) throws IOException {
        try {
            if (twoPlayers && model.getCount() !=0) {
                model.addModelListener(proxy, 1, session);
                proxy.setViewListener(model);
                twoPlayers = false;
            } else {
                model = new ConnectModel();
                model.addModelListener(proxy, 0, session);
                proxy.setViewListener(model);
                twoPlayers = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes an element/s off of the board for the current player
     * Exported to the model
     *
     * @throws IOException
     */
    @Override
    public void placed(int id, int x, int y) throws IOException {}

    /**
     * Starts a new game
     * @throws IOException
     */
    @Override
    public void newgame() throws IOException {}

    /**
     * Quits for any player still connected
     * Exported to the model
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {}


}