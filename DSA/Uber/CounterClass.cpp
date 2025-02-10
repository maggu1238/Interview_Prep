/*
Implement a Counter class that has the following methods:


put(number): put the number to the data structure
count(number): count the number of times number was put during the last window=5 minutes
countAll(): count the number of times any number was put during the last window=5 minutes.
*/

/*
Since we need to efficiently store and retrieve counts of numbers in the last 5-minute (300-second) window, we can use:

Hash Map (unordered_map<int, int>)
Tracks occurrences of each number in the last 300 seconds.
Queue (deque<pair<int, int>>)
Stores (timestamp, number) to efficiently remove old entries.
*/

#include <iostream>
#include <unordered_map>
#include <queue>

using namespace std;

class Counter {
private:
    queue<pair<int, int>> q;  // Stores (timestamp, number)
    unordered_map<int, int> numCount;  // Tracks occurrences of each number
    int totalCount = 0;  // Total count of numbers in last 5 min
    int timeWindow = 300;  // 5-minute window

    // Remove outdated entries
    void cleanup(int currentTime) {
        while (!q.empty() && currentTime - q.front().first >= timeWindow) {
            int expiredNum = q.front().second;
            q.pop();
            numCount[expiredNum]--;  // Reduce count of expired number
            totalCount--;  // Reduce total count
            if (numCount[expiredNum] == 0) {
                numCount.erase(expiredNum);  // Remove number if count is zero
            }
        }
    }

public:
    void put(int timestamp, int number) {
        cleanup(timestamp);  // Remove outdated entries
        q.push({timestamp, number});
        numCount[number]++;
        totalCount++;
    }

    int count(int timestamp, int number) {
        cleanup(timestamp);  // Remove outdated entries
        return numCount[number];
    }

    int countAll(int timestamp) {
        cleanup(timestamp);  // Remove outdated entries
        return totalCount;
    }
};

int main() {
    Counter counter;

    counter.put(1, 5);
    counter.put(2, 3);
    counter.put(4, 5);
    counter.put(300, 3);
    counter.put(301, 5);

    cout << counter.count(301, 5) << endl;  // Output: 2 (5 appeared twice)
    cout << counter.count(301, 3) << endl;  // Output: 1 (3 appeared once)
    cout << counter.countAll(301) << endl;  // Output: 3 (all within 5-min window)

    return 0;
}
