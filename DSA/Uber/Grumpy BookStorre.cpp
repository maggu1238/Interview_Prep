/**
 * 
 * There is a bookstore owner that has a store open for n minutes. You are given an integer array customers of length n where customers[i] is the number of the customers that enter the store at the start of the ith minute and all those customers leave after the end of that minute.

During certain minutes, the bookstore owner is grumpy. You are given a binary array grumpy where grumpy[i] is 1 if the bookstore owner is grumpy during the ith minute, and is 0 otherwise.

When the bookstore owner is grumpy, the customers entering during that minute are not satisfied. Otherwise, they are satisfied.

The bookstore owner knows a secret technique to remain not grumpy for minutes consecutive minutes, but this technique can only be used once.

Return the maximum number of customers that can be satisfied throughout the day.

 
 */

class Solution {
    public:
        int maxSatisfied(vector<int>& customers, vector<int>& grumpy, int minutes) {
    
            int window = 0;
            int maxWindow = INT_MIN;
            int l = 0;
            int satisfied = 0;
    
            for( int i =0; i < customers.size(); i++){
                // if owner is grumpy then customer is not satisfied in that window
                // treying to track all those unsatisfied customers in a window
                // jo satisfied h unhe alag se add krliya
                // finalyy jis bhi window me apne ko max unsatisfied mile unhe add kr diya 
    
                // agar apne ko wo minutes chaiye rhaene toh l, r bhi track kr skte
                if(grumpy[i] == 1){
                    window += customers[i];
                }
                else{
                    satisfied += customers[i];
                }
    
                if(i - l + 1 > minutes){
                    if(grumpy[l] == 1){
                        window -= customers[l];
                    }
                    l++;
                }
    
                maxWindow = max(maxWindow, window);
            }
    
            return satisfied + maxWindow;
            
        }
    };