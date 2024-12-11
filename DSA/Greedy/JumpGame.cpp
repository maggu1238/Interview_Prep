/*You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.

Return true if you can reach the last index, or false otherwise.*/
class Solution {
public:
    bool canJump(vector<int>& nums) {
        
        int n =  nums.size();
        
        int good = n-1;
        for(int i = n-2; i>=0; i--)
        {
            if(nums[i]+i >= n-1 || good <=nums[i]+i)
            {
                good = i;
            }
        }
        return good==0 ? true : false;
    }
};
