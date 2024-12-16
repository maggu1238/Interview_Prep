
/**Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1]. */
class Solution {
    int findLowerOrUpper(int[] nums, int lower, int target){

        int low = 0;
        int high = nums.length - 1;

        while(low <= high){
            int mid = low + (high  - low)/2;

            if(nums[mid] > target){
                high = mid - 1;
            }
            else if(nums[mid] < target){
                low = mid + 1;
            }
            else{
                if(lower == 1){
                    high = mid -1;
                }
                else{
                    low = mid + 1;
                }
            }
        }

        if(lower == 1){
            return high;
        }

        return low;
    }
    public int[] searchRange(int[] nums, int target) {

        if(nums.length == 0){
            return new int[]{-1, -1};
        }

        int low = 0;
        int high = nums.length - 1;
        int lower = findLowerOrUpper(nums, 1, target);
        int higher = findLowerOrUpper(nums, 0, target);

        if(lower + 1 < nums.length && nums[lower + 1] == target){
            lower = lower +1;
        }else{
            lower = -1;
        }
        
        if(higher - 1 >=0 && nums[higher - 1] == target){
            higher = higher - 1;
        }
        else{
            higher = -1;
        }

        return new int[]{lower, higher};
        
    }
}