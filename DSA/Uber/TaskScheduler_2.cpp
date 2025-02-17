/*
You are given a 0-indexed array of positive integers tasks, representing tasks that need to be completed in order, where tasks[i] represents the type of the ith task.

You are also given a positive integer space, which represents the minimum number of days that must pass after the completion of a task before another task of the same type can be performed.

Each day, until all tasks have been completed, you must either:

Complete the next task from tasks, or
Take a break.
Return the minimum number of days needed to complete all tasks.
*/


class Solution {
    public:
        long long taskSchedulerII(vector<int>& tasks, int space) {
            // containing the day when the last time task was performed
            unordered_map<int, int> lastSeen;
    
    
            long long result = 0;
            for( int i=0; i < tasks.size(); i++){
                int task =  tasks[i];
                long long currDay = result + 1;
                if(lastSeen.find(task) == lastSeen.end()){
                    result = currDay;
                }
                else{
                    int duration =  currDay - lastSeen[task];
                    if(duration <= space){
                        // update the currDaty so that it matches our condition
                        // there is space amount of gap
                        currDay +=  space - duration + 1;
                    }
                    result = currDay;
                }
                //cout<<result<<endl;
                lastSeen[task] = result;
            }
    
            return result;
        }
    };