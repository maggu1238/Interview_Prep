#include <iostream>
#include <vector>
#include <unordered_map>
#include <queue>

using namespace std;

// Function to find the conversion rate using BFS
double findConversionRate(vector<vector<string>> &rates, string from, string to) {
    unordered_map<string, vector<pair<string, double>>> graph;

    // Build the graph
    for (auto &rate : rates) {
        string src = rate[0], dest = rate[1];
        double value = stod(rate[2]);

        graph[src].push_back({dest, value});
        graph[dest].push_back({src, 1.0 / value}); // Reverse rate for bidirectional conversion
    }

    // BFS to find the conversion rate
    queue<pair<string, double>> q;
    unordered_map<string, bool> visited;
    q.push({from, 1.0});
    visited[from] = true;

    while (!q.empty()) {
        auto [curr, value] = q.front();
        q.pop();

        if (curr == to) return value; // Found conversion

        for (auto &[neighbor, rate] : graph[curr]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                q.push({neighbor, value * rate});
            }
        }
    }

    return -1.0; // Conversion not possible
}

// Example usage
int main() {
    vector<vector<string>> rates = {
        {"USD", "GBP", "0.77"},
        {"GBP", "EUR", "1.17"},
        {"EUR", "JPY", "129.53"}
    };

    cout << findConversionRate(rates, "USD", "JPY") << endl; // Expected: 0.77 * 1.17 * 129.53
    cout << findConversionRate(rates, "EUR", "USD") << endl; // Expected: 1 / (0.77 * 1.17)

    return 0;
}


#include <iostream>
#include <vector>
#include <unordered_map>
#include <queue>

using namespace std;

// Function to find the maximum conversion rate
double maxConversionRate(vector<vector<string>> &rates, string from, string to) {
    unordered_map<string, vector<pair<string, double>>> graph;

    // Build the graph
    for (auto &rate : rates) {
        string src = rate[0], dest = rate[1];
        double value = stod(rate[2]);

        graph[src].push_back({dest, value});
        graph[dest].push_back({src, value}); // Keep the same rate since we want max product path
    }

    // Max Heap (priority queue)
    priority_queue<pair<double, string>> pq; // {max conversion rate, currency}
    unordered_map<string, double> maxRate; // Store the max conversion rate found

    pq.push({1.0, from});
    maxRate[from] = 1.0;

    while (!pq.empty()) {
        auto [currentRate, currency] = pq.top();
        pq.pop();

        if (currency == to) return currentRate; // Found max conversion to target

        for (auto &[neighbor, rate] : graph[currency]) {
            double newRate = currentRate * rate;
            if (newRate > maxRate[neighbor]) { // Only update if it's better
                maxRate[neighbor] = newRate;
                pq.push({newRate, neighbor});
            }
        }
    }

    return -1.0; // Conversion not possible
}

// Example usage
int main() {
    vector<vector<string>> rates = {
        {"USD", "GBP", "0.77"},
        {"GBP", "EUR", "1.2"},
        {"EUR", "JPY", "130"},
        {"USD", "JPY", "100"}
    };

    cout << maxConversionRate(rates, "USD", "JPY") << endl; // Expected max path conversion rate
    cout << maxConversionRate(rates, "GBP", "JPY") << endl; // Expected: 1.2 * 130

    return 0;
}
