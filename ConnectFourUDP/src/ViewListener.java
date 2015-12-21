import java.io.IOException;

/**
 * Created by michael on 6/11/2015.
 */
public interface ViewListener {

    public void join(ViewProxy proxy, String name) throws IOException;

    public void newgame() throws IOException;

    public void quit() throws IOException;

    public void placed(int id, int x, int y) throws IOException;
}
