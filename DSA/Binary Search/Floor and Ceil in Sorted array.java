

/*Given an unsorted array arr[] of integers and an integer x, find the floor and ceiling of x in arr[].

Floor of x is the largest element which is smaller than or equal to x. Floor of x doesn’t exist if x is smaller than smallest element of arr[].
Ceil of x is the smallest element which is greater than or equal to x. Ceil of x doesn’t exist if x is greater than greatest element of arr[].

Return an array of integers denoting the [floor, ceil]. Return -1 for floor or ceiling if the floor or ceiling is not present. */
class Solution {
    public int[] getFloorAndCeil(int x, int[] arr) {
        // code here
        
        int low = 0;
        int high = arr.length - 1;
        
        Arrays.sort(arr);
        
        
                    //System.out.println(low + " " + high);

        while(low <= high){
            int mid = low + (high - low)/2;
            
           // System.out.println(low + " " + high + " " + mid);
            
            if(arr[mid] > x){
                high = mid - 1;
            }
            else if(arr[mid] < x){
                low = mid + 1;
            }
            else{
                
                return new int[] {arr[mid],arr[mid]};
            }
        }
        
        if(high < 0){
            high = -1;
        }
        else{
            high = arr[high];
        }
        
        if(low >= arr.length){
            low = -1;
        }
        else{
            low = arr[low];
        }
        
        return new int [] {high,low};
    }
}
