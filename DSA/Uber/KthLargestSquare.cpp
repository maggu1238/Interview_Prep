/*
Given an array of +ve and -ve integers.Find the kth largest square element
*/

int findPivot(vector<int> nums){
    int low = 0;
    int high = nums.size() - 1;

    while(low <= high){
        int mid = low + (high -low)/2;
        if(nums[mid] < 0 && nums[mid+1] >= 0 ){
            return mid;
        }
        if(arr[mid]  < 0){
            low = mid  + 1;
        }
        else{
            high = mid - 1;
        }

    }

    return -1;
}


binarySearch(){
    int left = k;

    // first +ve number
    int low = pivot + 1;
    // last +ve number
    int high = n-1;
    while(low <= high){
        int mid = low + (high-low)/2;

        int elementsRight =  mid - low;
        int 
    }
}
int solve(vector<int> nums, int k) {
	int n = nums.size();

    if(nums[n-1] < 0){
        return nums[n-k];
    }

    if(nums[0] >= 0){
        return nums[k-1];
    }

    // this pivot tells about the first -ve number where after +ve or zero is present
    int pivot = findPivot(nums);

    // now we have two sorted arrays
    // one on the right side of the pivot 
    // anoteher on the left side of the pivot starttng back from pivot
    return binarySearch(pivot, k, n, nums);
}
