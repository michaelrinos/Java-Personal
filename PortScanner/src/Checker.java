import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by michael on 2/9/2015.
 */

public class Checker {
    private static int timeout = 500;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length < 2 ){
            System.out.print("usage: java PortScanner ipAddress <number> <number> ... and or <range(num-num)>");
            System.exit(1);
        }
        ArrayList<Integer> individual = new ArrayList<>();
        ArrayList<Integer> range = new ArrayList<>();
        for (String s: args) {

            String[] parts = s.split("-");
            if (parts.length < 2){
                try {
                    individual.add(Integer.parseInt(s));
                }
                catch (NumberFormatException e){

                }
            }
            else if (parts.length ==2){
                range.add(Integer.parseInt(parts[0]));
                range.add(Integer.parseInt(parts[1]));
            }
            else{
                range.add(Integer.parseInt(parts[0]));
                range.add(Integer.parseInt(parts[parts.length]));
            }

        }
        System.out.println("The individual items");
        for (int i = 0; i <individual.size(); i++){
            System.out.println(individual.get(i));
        }
        System.out.println("The range items");
        for (int i = 0; i <range.size(); i++){
            System.out.println(range.get(i));
        }



        long stopwatch = -System.currentTimeMillis();
        ExecutorService pool = Executors.newCachedThreadPool();
        LinkedList<Future<PortScanner>> results = new LinkedList<>();

        for(int i = 0; i < range.size(); i+=2){
                for (int j = range.get(i); j<range.get(i+1);j++){
                    results.add(pool.submit(new PortScannerCallable(args[0], j, 200)));
                }
        }

        for (Future<PortScanner> result : results){
            PortScanner scaner = result.get();
            System.out.print("Port " + scaner.getPort() + ": ");
            if (scaner.getTrueFalse()) {
                System.out.println("Open");
            }
            else{
                System.out.println("Closed");
            }
        }

        pool.shutdown();
        stopwatch += System.currentTimeMillis();
        System.out.print("Time till completion: " + stopwatch);

    }
}
