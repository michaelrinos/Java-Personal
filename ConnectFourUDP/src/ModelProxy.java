import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;


/**
 * Class ModelProxy provides the network proxy for the model object in the
 * Network Go Game. The model proxy resides in the client program and
 * communicates with the server program.
 *
 * @author  Alan Kaminsky
 * @version 21-Jan-2010
 */
public class ModelProxy implements ViewListener {

// Hidden data members.

    private DatagramSocket mailbox;
    private SocketAddress destination;
    private ModelListener modelListener;

// Exported constructors.

    /**
     * Construct a new model proxy.
     *
     * @throws IOException Thrown if an I/O error occurred.
     */
    public ModelProxy(DatagramSocket mailbox, SocketAddress destination) throws IOException {
        this.mailbox = mailbox;
        this.destination = destination;
    }

// Exported operations.

    /**
     * Set the model listener object for this model proxy.
     *
     * @param modelListener Model listener.
     */
    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
        new ReaderThread().start();
    }

    /**
     * Tells server someone has joined
     */
    @Override
    public void join
    (ViewProxy proxy,
     String session)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('J');
        out.writeUTF(session);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send
                (new DatagramPacket(payload, payload.length, destination));
    }

    /**
     * tells server that a player took these numbers from a heap
     *
     * @param x the heap
     * @param y the number of items removed
     * @throws IOException
     */

    @Override
    public void placed(int id, int x, int y) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('P');
        out.writeInt(id);
        out.writeInt(x);
        out.writeInt(y);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send
                (new DatagramPacket(payload, payload.length, destination));
    }

    /**
     * Tells server to start a new game
     *
     * @throws IOException
     */
    @Override
    public void newgame() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('N');
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send
                (new DatagramPacket(payload, payload.length, destination));
    }

    /**
     * Tells server to quit the game
     *
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('Q');
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send
                (new DatagramPacket(payload, payload.length, destination));
    }

// Hidden helper classes.

    /**
     * Class ReaderThread receives messages from the network, decodes them, and
     * invokes the proper methods to process them.
     */
    private class ReaderThread
            extends Thread {
        public void run() {
            byte[] payload = new byte[128]; /* CAREFUL OF BUFFER SIZE! */
            try {
                for (;;) {
                    DatagramPacket packet =
                            new DatagramPacket(payload, payload.length);
                    mailbox.receive(packet);
                    DataInputStream in =
                            new DataInputStream
                                    (new ByteArrayInputStream
                                            (payload, 0, packet.getLength()));
                    int r, c;
                    byte b = in.readByte();
                    switch (b) {
                        case 'I':
                            int id = in.readByte();
                            modelListener.id(id);
                            break;
                        case 'N':
                            id = in .readByte();
                            String name =  in.readUTF();
                            modelListener.name(id, name);
                            break;
                        case 'S':
                            id = in.readByte();
                            int score = in.readByte();
                            modelListener.score(id, score);
                            break;
                        case 'H':
                            modelListener.reset();
                            break;
                        case 'M':
                            id = r = in.readByte();
                            r = in.readByte();
                            c = in.readByte();
                            modelListener.move(id, r, c);
                            break;
                        case 'T':
                            id = in.readByte();
                            modelListener.turn(id);
                            break;
                        case 'W':
                            id = in.readByte();
                            modelListener.win(id);
                            break;
                        case 'Q':
                            modelListener.quit();
                            break;
                        default:
                            System.err.println("Bad message");
                            break;
                    }
                }
            } catch (IOException exc) {
            } finally {
                mailbox.close();
            }
        }
    }
}


