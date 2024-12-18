/*You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.

For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.

Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.*/
int MinimumBuses(vector<vector<int>>& routes, int src, int dest) {
    unordered_map<int, vector<int>> adjList;
    for (int i = 0; i < routes.size(); i++) {
        for (int station : routes[i]) {
            if (adjList.find(station) == adjList.end()) {
                adjList[station] = vector<int>();
            }
            adjList[station].push_back(i);
        }
    }

    deque<pair<int, int>> queue;
    queue.push_back({src, 0});
    unordered_set<int> visitedBuses;

    while (!queue.empty()) {
        int station = queue.front().first;
        int busesTaken = queue.front().second;
        queue.pop_front();

        if (station == dest) {
            return busesTaken;
        }

        if (adjList.find(station) != adjList.end()) {
            for (int bus : adjList[station]) {
                if (visitedBuses.find(bus) == visitedBuses.end()) {
                    for (int s : routes[bus]) {
                        queue.push_back({s, busesTaken + 1});
                    }
                    visitedBuses.insert(bus);
                }
            }
        }
    }

    return -1;
}