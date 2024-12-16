/*A conveyor belt has packages that must be shipped from one port to another within days days.

The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within days days. */

class Solution {
    int daysRequired(int shipWeight, int[] weights){

        int days = 0;
        int tempSum = 0;
        for( int i =0; i < weights.length; i++){
            if(weights[i] + tempSum  <= shipWeight){
                tempSum += weights[i]; 
            }
            else{
                days++;
                tempSum = weights[i];
            }
        }
        if(tempSum <= shipWeight)
            days++;
        return days;
    }

    public int shipWithinDays(int[] weights, int days) {
        int sum = 0;
        int maxWeight = Integer.MIN_VALUE;

        for(int it : weights){
            sum += it;
            maxWeight = Math.max(maxWeight, it);
        }

        int low = maxWeight;
        int high = sum;

        while(low <= high){
            int mid = low + (high - low) /2;

            if(daysRequired(mid, weights) > days){
                low = mid + 1;
            }
            else{
                high = mid - 1;
            }
        }

        return low;

    }
}