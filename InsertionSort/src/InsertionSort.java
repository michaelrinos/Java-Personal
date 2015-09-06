import java.util.ArrayList;

/**
 * Created by michael on 31/8/2015.
 */
public class InsertionSort {
    public static void main(String[] args) {
        ArrayList<Integer> List = new ArrayList<Integer>();
        for (int i = 100; i > 0; i--) {
            List.add(i);
        }
        System.out.println("The unsorted list: ");
        for (int i = 0; i < List.size(); i++) {
            System.out.print(List.get(i) + " ");
        }

        List = InsertionSort(List);
        System.out.println("The Sorted List: ");
        for (int i = 0; i < List.size(); i++) {
            System.out.print(List.get(i) + " ");
        }
    }
    public static ArrayList<Integer> InsertionSort(ArrayList<Integer> list){
        Integer length = list.size();
        for (int i  = 0; i < length; i++){
            Integer x = i;
            while (x > 0 && list.get(x) < list.get(x-1) ) {
                Integer temp = list.get(x);
                list.set(x, list.get(x - 1));
                list.set(x - 1, temp);
                x = x - 1;
            }
        }
        return list;
    }
}
