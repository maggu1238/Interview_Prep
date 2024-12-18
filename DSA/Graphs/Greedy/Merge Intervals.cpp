/*Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.*/
std::vector<std::vector<int>> MergeIntervals(std::vector<std::vector<int>> &intervals)
{
    std::vector<std::vector<int>> output;

    int overlappingIndex = 0 ;
    // Replace this placeholder return statement with your code
    for(int i=0; i < intervals.size(); i++){
        int firstElement = intervals[i][0];
        int currLastElement = intervals[i][1];

        int lastElementForOverlappingInterval = intervals[overlappingIndex][1];
        if(lastElementForOverlappingInterval >= firstElement){
            intervals[overlappingIndex][1] = max(lastElementForOverlappingInterval, currLastElement);
        }
        else{
            output.push_back(intervals[overlappingIndex]);
            overlappingIndex = i;
        }
        
        if (i == intervals.size() - 1){
            output.push_back(intervals[overlappingIndex]);
        }
    }
    return output;
}