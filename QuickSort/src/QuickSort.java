import java.util.ArrayList;

/**
 * Created by michael on 2/9/2015.
 */

public class QuickSort {
    public static void main(String[] args) {
        ArrayList<Integer> List = new ArrayList<Integer>();
        for (int i = 100; i > 0; i--){
            List.add(i);
        }
        System.out.println("The unsorted list: ");
        for (int i =0; i < List.size(); i++){
            System.out.print(List.get(i)+ " ");
        }

        List = QuickSort(List, 0, List.size()-1);
        System.out.println("The Sorted List: ");
        for ( int i = 0; i < List.size(); i++){
            System.out.print(List.get(i)+ " ");
        }
    }
    public static ArrayList<Integer> QuickSort(ArrayList<Integer> x, Integer start, Integer end)
    {
        if (start<end){
            Integer pivot=partition(x,start,end);
            QuickSort(x,pivot+1,end);
            QuickSort(x,start,pivot-1);
        }
        return x;
    }
    public static Integer partition(ArrayList<Integer> list, Integer start, Integer end)
    {
        int pivot = list.get(start);
        do
        {
            while(start<end && list.get(end)>=pivot)
                end--;
            if (start<end) list.set(start,list.get(end));
            while(start<end && list.get(start)<=pivot)
                start++;
            if (start<end) list.set(end,list.get(start));
        }
        while(start<end);
        list.set(start,pivot);

        return start;
    }
}
