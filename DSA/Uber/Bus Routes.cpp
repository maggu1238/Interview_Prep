/*
You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.

For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.

Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.
*/

class Solution {
    public:
        int numBusesToDestination(vector<vector<int>>& routes, int S, int T) {
    
        if (S == T) return 0; // Already at destination
    
        unordered_map<int, vector<int>> stopToRoutes; // Stop -> Routes
        for (int i = 0; i < routes.size(); i++) {
            for (int stop : routes[i]) {
                stopToRoutes[stop].push_back(i);
            }
        }
    
        queue<pair<int, int>> q; // (stop, buses taken)
        unordered_set<int> visitedStops, visitedRoutes;
        q.push({S, 0});
        visitedStops.insert(S);
    
        while (!q.empty()) {
            auto [curStop, buses] = q.front();
            q.pop();
    
            for (int route : stopToRoutes[curStop]) {
                if (visitedRoutes.count(route)) continue; // Avoid revisiting routes
                visitedRoutes.insert(route);
    
                for (int nextStop : routes[route]) {
                    if (nextStop == T) return buses + 1; // Found destination
                    if (!visitedStops.count(nextStop)) {
                        visitedStops.insert(nextStop);
                        q.push({nextStop, buses + 1});
                    }
                }
            }
        }
            
    
     return -1;
            
        }
    };