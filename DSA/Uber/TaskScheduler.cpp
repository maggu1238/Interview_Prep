/*
You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n. 
Each CPU interval can be idle or allow the completion of one task. 
Tasks can be completed in any order, but there's a constraint: there has to be a gap of at least n intervals between two tasks with the same label.

Return the minimum number of CPU intervals required to complete all tasks.
*/


class Solution {
    public:
        int leastInterval(vector<char>& tasks, int n) {
            unordered_map<char,int> freq;
            for(char task: tasks){
                freq[task]++;
            }
    
            int maxFreq = 0;
            int maxCount = 0;
            for(const auto& entry: freq){
                if(entry.second > maxFreq){
                    maxFreq = entry.second ;
                    maxCount = 1;
                }
                else if( entry.second  == maxFreq){
                    maxCount++;
                }
            }
    
            // finding the number of partitions req
            // subtracting 1 because in the last parttition we don't have to wait so 
            // we try to fill the remaining tasks in the previous partitions
            int partCount = maxFreq - 1;
    
            // finding the length of a partition which is empty
            // ek task agar h usme maxCount wala toh length of the partition km hogi 
            int partLength = n -(maxCount - 1);
    
            // finding empty slots i.e number of partitions * length of partittion which is empty 
            // where those tasks can be filled havbing freq < maxFreq
            int emptySlots = partCount * partLength;
    
    
            int remainingTasks = tasks.size() - maxFreq * maxCount;
    
            int idles = max(0, emptySlots - remainingTasks);
    
            return tasks.size() + idles;
         }
    };
