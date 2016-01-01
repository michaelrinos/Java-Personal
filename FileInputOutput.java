import java.io.*;
import java.util.ArrayList;


/**
 * Created by michael on 30/12/2015.
 * @Description: Program which takes a file and sorts it based on the number before the ":"
 */
public class FileInputOutput {
    private static ArrayList<Question> contentsArray1 = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        if (args.length != 1) usage();
        String content1 = null;

        String file1 = args[0];
        FileReader File1 = new FileReader(file1);
        BufferedReader reader1 = new BufferedReader(File1);

        while ((content1 = reader1.readLine()) != null) {
            String[] parts = content1.split(":");
            Question temp = new Question(Integer.parseInt(parts[0]), parts[1]);
            contentsArray1.add(temp);
        }
        reader1.close();                                                        //Be Kind Rewind!!!
        contentsArray1.sort(Question::compareTo);                               //Sorts Arrays
        Writer output1 = new BufferedWriter(new FileWriter(new File(file1)));   //Its Shakespeare

        for (Question x :contentsArray1) {                                      //Actually writing
            output1.write(x.getId() + ": " + x.getInfo() + "\n");                //
            output1.flush();                                                    //
        }

        output1.close();                                                        //Be Kind Rewind!!!

    }

    private static void usage(){
        System.out.println("Usage <filepath1>");
        System.exit(1);
    }
}
