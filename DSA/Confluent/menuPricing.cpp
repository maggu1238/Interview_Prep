#include <bits/stdc++.h>
using namespace std;

unordered_map<string, int> itemIndex; // Maps food items to bit positions

// Function to convert a list of items to a bitmask
int getMask(vector<string>& items) {
    int mask = 0;
    for (string& item : items) {
        if (itemIndex.find(item) != itemIndex.end()) {
            mask |= (1 << itemIndex[item]); // Set the corresponding bit
        }
    }
    return mask;
}

// Function to find the minimum cost to obtain the required set of items
double findMinCost(vector<pair<double, string>>& menuItems, vector<string>& userWants) {
    int idx = 0; // Bit position counter

    // Assign unique bit positions to each food item
    for (auto& [price, items] : menuItems) {
        stringstream ss(items);
        string item;
        while (getline(ss, item, ',')) {
            if (!itemIndex.count(item)) {
                itemIndex[item] = idx++; // Assign a new index
            }
        }
    }

    int totalItems = itemIndex.size();
    int fullMask = (1 << totalItems); // Total possible combinations (2^n)

    vector<int> mealMasks;   // Stores bitmasks of menu items
    vector<double> mealPrices; // Stores corresponding prices

    // Convert menu items to bitmasks
    for (auto& [price, items] : menuItems) {
        stringstream ss(items);
        vector<string> mealItems;
        string item;
        while (getline(ss, item, ',')) {
            mealItems.push_back(item);
        }
        mealMasks.push_back(getMask(mealItems));
        mealPrices.push_back(price);
    }

    // Convert user_wants to a bitmask
    int requiredMask = getMask(userWants);

    // DP Array: dp[mask] stores the minimum cost to obtain the set of items represented by "mask"
    vector<double> dp(fullMask, INT_MAX);
    dp[0] = 0; // Base case: Cost of ordering nothing is 0

    // DP Transition: Try all menu items and update costs
    for (int mask = 0; mask < fullMask; ++mask) {
        if (dp[mask] == INT_MAX) continue; // Skip uninitialized states

        for (size_t i = 0; i < mealMasks.size(); i++) {
            int newMask = mask | mealMasks[i]; // Combine current mask with meal
            dp[newMask] = min(dp[newMask], dp[mask] + mealPrices[i]);
        }
    }

    return dp[requiredMask]; // Return the minimum cost to fulfill user_wants
}

int main() {
    // Menu: {price, "items"}
    vector<pair<double, string>> menuItems = {
        {5.00, "pizza"},
        {8.00, "sandwich, coke"},
        {4.00, "pasta"},
        {2.00, "coke"},
        {6.00, "pasta, coke, pizza"},
        {8.00, "burger, coke, pizza"},
        {5.00, "sandwich"}
    };

    // User's requested items
    vector<string> userWants = {"burger", "pasta"};

    // Compute the minimum cost
    double minCost = findMinCost(menuItems, userWants);
    
    // Print the output
    cout << fixed << setprecision(2) << minCost << endl;

    return 0;
}
