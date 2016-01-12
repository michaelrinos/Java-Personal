/**
 * Created by michael on 27/12/2015.
 */

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;


public class OkCupid {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) usage();
        String url = args[0];
        String x= url;


        Connection.Response res = Jsoup.connect("https://www.okcupid.com/login")
                .method(Connection.Method.POST)
                .data("username", "<USERID>")               //Login in username
                .data("password", "<PASSWORD>")             //Login password
                .execute();                                 //Submits the form


        /*PrintWriter writer = new PrintWriter(url.split("/")[4]+".txt", "UTF-8");*/
        Writer output = null;
        File file = new File("/home/michael/Documents/"+url.split("/")[4]+".txt");
        file.createNewFile();
        output = new BufferedWriter(new FileWriter(file));

        int count = 11;
        while(true) {

            Document doc = Jsoup.connect(x)
                    .cookies(res.cookies()).get();
            ArrayList<Element> question = doc.select("div[id^=qtext_]");
            ArrayList<Element> answer = doc.select("span[id^=answer_target]");
            if (answer.size() == 0 || question.size() == 0){
                break;
            }
            for (int i = 0; i < question.size(); i ++) {
                output.write(question.get(i).toString().split("qtext_|\"")[2]+
                        ": "+question.get(i).toString().split("p>|</")[1]+"\n"); // Write to file
                output.write(answer.get(i).toString().split("answer_target_|\"")[2]+
                        ": "+answer.get(i).toString().split("<|>")[2]+"\n");
                output.flush();
            }
            x = url + "?low="+count+"&n=7";
            count +=10;
        }

    }

    public static void usage() {
        System.out.println("Usage: java OkCupid <url for persons questions>");
        System.exit(1);
    }
}