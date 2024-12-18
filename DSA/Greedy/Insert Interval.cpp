/*You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent the start and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval newInterval = [start, end] that represents the start and end of another interval.

Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return intervals after the insertion.

Note that you don't need to modify intervals in-place. You can make a new array and return it.*/

vector<vector<int>> InsertInterval(vector<vector<int>> existingIntervals, vector<int> newInterval)
{
    vector<vector<int>> output;
    output.push_back(newInterval);

    // Replace this placeholder return statement with your code
    for(int i = 0; i < existingIntervals.size(); i++){
        if(existingIntervals[i][0] > output.back()[1]){
            output.push_back(existingIntervals[i]);
        }
        else if(existingIntervals[i][0] <= output.back()[1] && existingIntervals[i][0] >= output.back()[0]){
            output.back()[1] = max(output.back()[1], existingIntervals[i][1]);
        }
        else{
            if (existingIntervals[i][1] < output.back()[0]){
                output.insert(output.end() - 1, existingIntervals[i]);
            }
            else{
                output.back()[0] = existingIntervals[i][0];
                output.back()[1] =  max(existingIntervals[i][1], output.back()[1]);
            }
        }
    }
    return output;
}