/*An online shopping website has a widget displaying the most popular items bought.
There is API service that sends a message contains (customer ID, item ID, timestamp) after each purchase transaction. Find the most popular items to be displayed on the widget. 
the top K items should updated every N minutes (let's say 10 mins), because it's a widget, and it should change with time.
The API service sends million of logs every minute, so the the required code should be flexible, and doesn't save everything in the memory first.	*/

#include <iostream>
#include <unordered_map>
#include <queue>
#include <vector>
#include <chrono>

using namespace std;

class PopularItems {
private:
    unordered_map<int, int> itemFrequency;  // Item ID → Purchase Count
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap;
    int K;  // Number of top items
    chrono::steady_clock::time_point lastUpdate;
    int updateInterval;  // Update frequency in seconds

public:
    PopularItems(int k, int interval) : K(k), updateInterval(interval) {
        lastUpdate = chrono::steady_clock::now();
    }

    void processPurchase(int itemId) {
        itemFrequency[itemId]++;

        if (minHeap.size() < K || itemFrequency[itemId] > minHeap.top().first) {
            minHeap.push({itemFrequency[itemId], itemId});
            if (minHeap.size() > K) minHeap.pop();
        }

        // Update every N minutes
        auto now = chrono::steady_clock::now();
        if (chrono::duration_cast<chrono::seconds>(now - lastUpdate).count() >= updateInterval) {
            updateTopK();
            lastUpdate = now;
        }
    }

    void updateTopK() {
        vector<pair<int, int>> topItems;
        while (!minHeap.empty()) {
            topItems.push_back(minHeap.top());
            minHeap.pop();
        }
        cout << "Top " << K << " Items: ";
        for (auto &item : topItems) {
            cout << "Item " << item.second << " (Count: " << item.first << ") ";
        }
        cout << endl;
        
        // Rebuild heap
        for (auto &item : topItems) minHeap.push(item);
    }
};

int main() {
    PopularItems tracker(3, 600); // Top 3 items, update every 10 minutes (600s)

    tracker.processPurchase(101);
    tracker.processPurchase(102);
    tracker.processPurchase(101);
    tracker.processPurchase(103);
    tracker.processPurchase(101);
    tracker.processPurchase(104);
    tracker.processPurchase(102);

    return 0;
}
