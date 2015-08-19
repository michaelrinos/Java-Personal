import java.util.ArrayList;
import java.util.Scanner;

public class FindPrime {
	/**
	 * 
	 * 
	 * 
	 */
	public static ArrayList<Integer> GetPrimes (int N){
		ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
		ArrayList<Integer> available = new ArrayList<Integer>();
		
		for (int i =2; i<= N; i++){
			available.add(i);
		}
		while(available.size()>0){
			ArrayList<Integer> remaining = new ArrayList<Integer>();
			primeNumbers.add(available.get(0));
			for (int i = 0; i < available.size();i++){
				if (available.get(i)%primeNumbers.get(primeNumbers.size()-1)!=0)
					remaining.add(available.get(i));
			}
			available = remaining;
		}
		return primeNumbers;
		
	}
	public static void main(String[] args) {
		System.out.print("Compute prime numbers from 2 to: ");
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		in.close();
		if (N<2){
			System.out.println("N must be greater than or equal to 2.");
		}
		else{
			ArrayList<Integer> primeNumbers = GetPrimes(N);
			System.out.print("Prime Numbers: ");
			for (Integer i : primeNumbers) {
				System.out.print(i+ " ");
			}
		}
		
	}
}
