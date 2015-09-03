import java.util.ArrayList;

/**
 * Created by michael on 31/8/2015.
 */
public class BubbleSort {
    public static void main(String[] args) {
        ArrayList<Integer> List = new ArrayList<Integer>();
        for (int i = 100; i > 0; i--){
            List.add(i);
        }
        System.out.println("The unsorted list: ");
        for (int i =0; i < List.size(); i++){
            System.out.print(List.get(i)+ " ");
        }

        List = BubbleSort(List);
        System.out.println("The Sorted List: ");
        for ( int i = 0; i < List.size(); i++){
            System.out.print(List.get(i)+ " ");
        }

    }
    public static ArrayList<Integer> BubbleSort(ArrayList<Integer> list){
        Integer length = list.size();
        for (int i  = 0; i < length-1; i++){
            for ( int j = 0; j < length-1; j++){
                if (list.get(j) > list.get(j+1)){
                    Integer temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);

                }
            }
        }
        return list;
    }
}
