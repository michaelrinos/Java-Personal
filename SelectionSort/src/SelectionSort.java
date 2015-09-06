import java.util.ArrayList;

/**
 * Created by michael on 31/8/2015.
 */
public class SelectionSort {
    public static void main(String[] args) {
        ArrayList<Integer> List = new ArrayList<Integer>();
        for (int i = 100; i > 0; i--){
            List.add(i);
        }
        System.out.println("The unsorted list: ");
        for (int i =0; i < List.size(); i++){
            System.out.print(List.get(i)+ " ");
        }

        List = SelectionSort(List);
        System.out.println("The Sorted List: ");
        for ( int i = 0; i < List.size(); i++){
            System.out.print(List.get(i)+ " ");
        }

    }

    public static ArrayList<Integer> SelectionSort(ArrayList<Integer> list){
        Integer length = list.size();
        for (int i  = 0; i < length-1; i++){
            Integer x = i;
            for ( int j = (i+1); j < length; j++){
                if (list.get(j) < list.get(x)) {
                    x = j;
                }
                Integer temp = list.get(x);
                list.set(x,list.get(i));
                list.set(i,temp);
            }
        }
        return list;
    }
}
