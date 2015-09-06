import java.util.concurrent.Callable;

/**
 * Created by michael on 2/9/2015.
 */

public class PortScannerCallable implements Callable<PortScanner> {
    private String address;
    private int port, timeout;
    private Boolean open_close;

    public PortScannerCallable(String address, int port, int timeout){
        this.address = address;
        this.port = port;
        this.timeout = timeout;
    }

    @Override
    public PortScanner call() {
        PortScanner scanner = new PortScanner(this.address,this.port,this.timeout);
        scanner.Scan();
        return scanner;
    }
}

