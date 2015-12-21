import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * Class ViewProxy provides the network proxy for the view object in the Network
 * Go Game. The view proxy resides in the server program and communicates with
 * the client program.
 *
 * @author  Alan Kaminsky
 * @version 28-Jan-2010
 */
public class ViewProxy implements ModelListener {

// Hidden data members.

    private DatagramSocket mailbox;
    private SocketAddress clientAddress;
    private ViewListener viewListener;

// Exported constructors.

    /**
     * Construct a new view proxy.
     *
     * @param  mailbox        Server's mailbox.
     * @param  clientAddress  Client's mailbox address.
     */
    public ViewProxy
    (DatagramSocket mailbox,
     SocketAddress clientAddress)
    {
        this.mailbox = mailbox;
        this.clientAddress = clientAddress;
    }

// Exported operations.

    /**
     * Set the view listener object for this view proxy.
     *
     * @param  viewListener  View listener.
     */
    public void setViewListener
    (ViewListener viewListener)
    {
        this.viewListener = viewListener;
    }


    /**
     * Process a received datagram.
     *
     * @param  datagram  Datagram.
     *
     * @return  True to discard this view proxy, false otherwise.
     *
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public boolean process(DatagramPacket datagram) throws IOException {
        boolean discard = false;
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(datagram.getData(), 0, datagram.getLength()));
        String session;
        int r, c;
        byte b = in.readByte();
        switch (b)
        {
            case 'J':
                session = in.readUTF();
                viewListener.join (ViewProxy.this, session);
                break;
            case 'P':
                int id = in.readInt();
                r = in.readInt();
                c = in.readInt();
                viewListener.placed (id, r, c);
                break;
            case 'N':
                viewListener.newgame();
                break;
            case 'Q':
                viewListener.quit();
                discard =true;
                break;
            default:
                System.err.println ("Bad message");
                break;
        }
        return discard;
    }


    // Implemented methods
    @Override
    public void id(int id) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('I');
        out.writeByte(id);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void name(int id, String name) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('N');
        out.writeByte(id);
        out.writeUTF(name);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void score(int id, int Score) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('S');
        out.writeByte(id);
        out.writeByte(Score);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(
                new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void reset() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('H');
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(
                new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void move(int id, int x, int y) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('M');
        out.writeByte(id);
        out.writeByte(x);
        out.writeByte(y);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(
                new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void turn(int id) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('T');
        out.writeByte(id);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(
                new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void win(int id) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('W');
        out.writeByte(id);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(
                new DatagramPacket(payload, payload.length, clientAddress));
    }

    @Override
    public void quit() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('Q');
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(
                new DatagramPacket(payload, payload.length, clientAddress));
    }
}