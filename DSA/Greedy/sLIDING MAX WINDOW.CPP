void CleanUp(int i, std::deque<int>& currentWindow, const std::vector<int>& nums) {
    // remove all the indexes from currentWindow whose corresponding values are smaller than or equal to the current element
    while (!currentWindow.empty() && nums[i] >= nums[currentWindow.back()]) {
        currentWindow.pop_back();
    }
}

// function to find the maximum in all possible windows
std::vector<int> FindMaxSlidingWindow(const std::vector<int>& nums, int w) {
    // if the length of input array is 1, return the input array
    if (nums.size()==1) {
        return nums;
    }

    // initializing variables
    std::vector<int> output;
    std::deque<int> currentWindow;

    // iterate over the first w elements
    for (int i = 0; i < w; ++i) {
        // for every element, remove the previous smaller elements from currentWindow
        CleanUp(i, currentWindow, nums);

        // append the index of the current element to currentWindow
        currentWindow.push_back(i);
    }

    // appending the maximum element of the current window to the output list
    output.push_back(nums[currentWindow[0]]);

    // iterate over the remaining input list
    for (int i = w; i < nums.size(); ++i) {
        // for every element, remove the previous smaller elements from currentWindow
        CleanUp(i, currentWindow, nums);

        // remove first index from the currentWindow if it has fallen out of the current window
        if (!currentWindow.empty() && currentWindow[0] <= (i - w)) {
            currentWindow.pop_front();
        }

        // append the index of the current element to currentWindow
        currentWindow.push_back(i);

        // adding the maximum element of the current window to the output list
        output.push_back(nums[currentWindow[0]]);
    }

    // return the array containing output
    return output;
}