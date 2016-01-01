/**
 * Created by michael on 30/12/2015.
 */
public class Question implements Comparable<Question> {
    private int id;
    private String info;

    public Question(int id, String info){
        this.id = id;
        this.info = info;
    }

    public String getInfo(){
        return info;
    }
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Question o) {
        return id - o.getId();
    }
}
