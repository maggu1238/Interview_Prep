//
//
//given an extremaly large arrray
//cal the sum of the
//
//
//print number
//        T1 -numberprinter
//        T2 - readnumber



//
//        class numberPriter extends Thread{
//           @Override
//    public  void run(){
//        for( int i=1; i< 5;i++)
//            System.out.println(i);
//    }
//        }
//
//        class Read
//        //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
///
///
class SumThread extends Thread{
    private int[] arr;
    private int start, end;
    private long partialSum;

    public SumThread(int arr[], int start, int end){
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for( int i= start; i < end; i++){
            partialSum += arr[i];
        }

        System.out.println("partial Sum " + start + " to " + end + " is:" + partialSum);
    }

    public long getPartialSum(){
        return partialSum;
    }
}


class Method{


    public static long sum(int[] arr) throws InterruptedException {
        int size = arr.length;
        int numThreads = 4;

        int chunks = size / numThreads;
        long totalSum =0;
        SumThread[] threads = new SumThread[numThreads];

        for(int i = 0; i < numThreads; i++){
            int start = i * chunks;
            int end = (i == numThreads - 1) ? size : start + chunks;

            threads[i] = new SumThread(arr, start, end);
            threads[i].start();
        }

        for(SumThread thread : threads){
            thread.join();
            totalSum += thread.getPartialSum();
        }

        //System.out.println(totalSum);
        return totalSum;
    }
}
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int arr[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        Method method = new Method();
        long total = method.sum(arr);

        System.out.println(total);

    }
}