import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by michael on 11/23/15.
 */
public class CountWords {
    private static Map<String, Integer> occurences = new HashMap<>();
    public static void main(String[] args){
        if (args.length !=1) usage();
        File file  = new File(args[0]);
        try {
            Scanner s = new Scanner(file);
            while(s.hasNext()){
                String word = s.next().toLowerCase();
                if (occurences.containsKey(word)){
                    occurences.put(word,occurences.get(word)+1);
                }
                else{
                    occurences.put(word,1);
                }
            }
            System.out.println(occurences);
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    private static void usage(){
        System.err.println ("Usage: java CountWords <filepath>");
        System.exit (1);
    }
}
