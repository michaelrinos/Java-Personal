import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Michael on 9/2/2015.
 */
public class PortScanner {
    private String address;
    private Boolean trueFalse;
    private int port, timeout;
    public PortScanner(String address, int port, int timeout){
        this.address = address;
        this.port = port;
        this.timeout = timeout;
    }
    public void Scan(){
        trueFalse = false;
        try {

            Socket sock = new Socket();
            sock.connect(new InetSocketAddress(address, port), timeout);
            trueFalse = true;
            sock.close();
        }
        catch (Exception e){
            trueFalse = false; }
    }

    public int getPort() {
        return port;
    }

    public Boolean getTrueFalse() {
        return trueFalse;
    }
}
