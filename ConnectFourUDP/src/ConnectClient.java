import java.io.IOException;
import java.net.*;

/**
 * ConnectClient is the main program, it starts the connection to the server
 * and initializes the model proxy and view
 *
 * Usage: java ConnectClient <I>serverhost</I> <I>serverport</I>
 *          <I>clienthost</I> <I>clientport</I> <I>playername</I>
 *
 * @author Michael Rinos
 */
public class ConnectClient {

    /**
     * Main program.
     * @exception throws and catches BindException in case the port is in use and all other exceptions caught
     */
    public static void main(String[] args){
        if (args.length != 5) {
            usage();
        }
        String serverhost = args[0];
        int serverport = Integer.parseInt(args[1]);
        String clienthost = args[2];
        int clientport = Integer.parseInt(args[3]);
        String session = args[4];

        try {
            DatagramSocket mailbox = new DatagramSocket(
                    new InetSocketAddress(clienthost, clientport));

            final ModelProxy proxy = new ModelProxy(mailbox,
                    new InetSocketAddress(serverhost, serverport));

            ConnectUI view = ConnectUI.create(session);
            proxy.setModelListener(view);
            view.setViewListener(proxy);
            proxy.join(null, session);
            view.reset();

        } catch (BindException e){
            System.err.println("The host or port is in use");
            System.exit(1);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java Nim <serverhost> <serverport> <clienthost> <clienport> <session>");
        System.exit (1);
    }

}