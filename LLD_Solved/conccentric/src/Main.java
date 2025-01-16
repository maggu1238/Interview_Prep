
import java.util.*;
public class Main {

    public static int generateRandomNumber(int n){
        Random random = new Random();
        return random.nextInt(n);
    }

    public static List<Integer> createList(int N){
        List<Integer> list = new ArrayList<>();
        for( int i =0; i < N; i++){
            list.add(i);
        }
        return list;
    }

    public static ArrayList<Integer> path(int N){

        List<Integer> a = createList(N);

        int tempSize = a.size();

        int visitedNumbers = 0;

        ArrayList<Integer> arr = new ArrayList<>();


        while(tempSize != 0){
            int randomNumber = generateRandomNumber(tempSize);
            int index = randomNumber + visitedNumbers;
           System.out.println("random-> " + randomNumber + " index -> " + index);


            Integer toSwap = a.get(index);
            int fromSwap =  visitedNumbers;

            a.set(index, a.get(fromSwap));
            arr.add(toSwap);

            a.set(fromSwap, toSwap);

           // System.out.println(a);
            tempSize--;
            visitedNumbers++;
        }

        return arr;
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");


        for( int i =0; i < 8; i++){
            List<Integer> arr = path(8);
            System.out.println(arr);
            System.out.println("_____________________________________________________");


        }
    }
}