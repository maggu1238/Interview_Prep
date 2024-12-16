/*Given a sorted array arr[] (with unique elements) and an integer k, find the index (0-based) of the largest element in arr[] that is less than or equal to k. This element is called the "floor" of k. If such an element does not exist, return -1.*/


class Solution {

    static int findFloor(int[] arr, int k) {
        // write code here
        
        int low = 0;
        int high = arr.length;
        
        while(low <= high){
            int mid = low + (high - low)/2;
            if(arr[mid] < k){
                low = mid + 1;
            }
            else if(arr[mid] > k){
                high = mid - 1;
            }
            else{
                return mid;
            }
        }
        
        if(high == -1){
            return -1;
        }
        
        return high;
    }
}