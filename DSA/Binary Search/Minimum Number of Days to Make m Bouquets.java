/*You are given an integer array bloomDay, an integer m and an integer k.

You want to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.

The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.

Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to make m bouquets return -1.*/
class Solution {
    int numberOfBouquets(int days, int k, int [] bloomDay){
        int cnt = 0;
        int bouqets = 0;

        for(int it : bloomDay){
            if(it <= days){
                cnt++;
            }
            else{
                cnt = 0;
                continue;
            }

            if(cnt == k){
                bouqets++;
                cnt = 0;
            }
        }

        return bouqets;
    }

    public int minDays(int[] bloomDay, int m, int k) {
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;

         //System.out.println(bloomDay.length + " " + m * k);
        if((long)bloomDay.length < (long)m * (long)k)
            return -1;

        for( int it : bloomDay){
            if(it < low){
                low = it;
            }
            if(it > high){
                high = it;
            }
        }

        while(low <= high){
            int mid = low + (high-low)/2;
            // cout<<low<<" "<<high <<" "<<mid<<endl;
            int bouquets = numberOfBouquets(mid, k, bloomDay);
           

            if(bouquets >= m){
                high = mid - 1;
            }
            else{
                low = mid  + 1;
            }
        }

        return low;
    }
}