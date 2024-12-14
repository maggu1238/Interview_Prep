/*Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours.

Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return the minimum integer k such that she can eat all the bananas within h hours.

 */

class Solution {
public:

    long possible(vector<int>& piles, int speed, int h){
        long  hours = 0;
        for (int i = 0; i < piles.size(); i++) {
            int temp  = (piles[i] / speed);
            hours += (long)temp;

            if(piles[i] % speed != 0){
                hours++;
            }
        }
        return hours;
    }


    int minEatingSpeed(vector<int>& piles, int h) {
        if(piles.size() > h)
            return -1;
        
        int maxSpeed;

        for(auto it : piles){
            maxSpeed = max(maxSpeed, it);
        }

        int left = 1;
        int right = maxSpeed;

        while(left <= right){
            int mid = left + (right-left)/2;
            long hours = possible(piles, mid, h);

            if(hours > (long)h){
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }

        return left;
        
    }
};