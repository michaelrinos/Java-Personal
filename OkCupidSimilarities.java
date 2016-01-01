import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 30/12/2015.
 */
public class OkCupidSimilarities {
    private static final String Destination = "/home/michael/Documents/OkCupid/";
    private static HashMap<Integer, String > Questions = new HashMap<>();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) usage();

        File Ofile = new File(Destination + "SimResults.txt");               //Preparing the new file
        Ofile.createNewFile();
        Writer output = new BufferedWriter(new FileWriter(Ofile));           //For writing output

        String content = null;
        ArrayList<HashMap<Integer, String>> Files = new ArrayList<>();
        int count = 0;
        int bigest = -1;
        HashMap<Integer,String> largest = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String file = args[i];
            output.write(file + "/");
            FileReader File = new FileReader(file);
            BufferedReader reader = new BufferedReader(File);
            HashMap<Integer, String> x = new HashMap<>();
            boolean question = true;                                        //Why waste memory keeping question text
                                                                            //When there's 10 files with 1000 Questions
                                                                            //That's a lot of wasted memory...
            while ((content = reader.readLine()) != null) {
                String[] parts = content.split(":");
                int key = Integer.parseInt(parts[0]);
                if (question){
                    Questions.put(key, parts[1]);
                    question = false;
                }else question = true;
                count +=1;
                x.put(key, parts[1]);
                if (count> bigest) largest = x;
            }
            output.write("\n");
            output.flush();
            count = 0;
            Files.add(x);
            reader.close();
        }

        for (Map.Entry<Integer,String> entry : largest.entrySet()){
            int key = entry.getKey();
            String value = entry.getValue();
            count = 0;
            int NA = 0;
            for (int i = 0; i < Files.size() ; i++) {
                String oValue = Files.get(i).get(key);
                if (value.equals(oValue) && !value.contains("Answer")){
                    count +=1;
                }else {
                    if (oValue == null){
                        NA+=1;
                    }
                }
            }
            if (count != 0) {
                output.write(Questions.get(key) +" Response:"+ largest.get(key)+
                        "Ratio: " +count + "/" + (Files.size()-NA)+ "\n");
                output.flush();
            }
        }
        output.close();
    }

    private static void usage() {
        System.out.println("Usage <N num of filepaths> <filepath for results>");
        System.exit(1);
    }
}
