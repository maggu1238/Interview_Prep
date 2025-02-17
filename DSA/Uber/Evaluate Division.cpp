/* You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.

You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.

Return the answers to all queries. If a single answer cannot be determined, return -1.0.

Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.

Note: The variables that do not occur in the list of equations are undefined, so the answer cannot be determined for them.*/



/*
Graph Construction:

Treat each variable as a node in a directed weighted graph.
If given A / B = value, create an edge from A → B with weight value.
Also, create a reverse edge from B → A with weight 1/value.
Graph Traversal (BFS/DFS):

For each query C / D, perform BFS or DFS to find a path from C to D.
The product of weights along this path gives the required division result.
If no such path exists, return -1.0.
*/



class Solution {
    public:
        vector<double> calcEquation(vector<vector<string>>& equations, vector<double>& values, vector<vector<string>>& queries) {
            unordered_map<string, vector<pair<string, double>>> graph;
    
            // Build the graph
            for (int i = 0; i < equations.size(); i++) {
                string A = equations[i][0], B = equations[i][1];
                double value = values[i];
    
                graph[A].push_back({B, value});
                graph[B].push_back({A, 1.0 / value});
            }
    
            vector<double> results;
            for (const auto& query : queries) {
                string C = query[0], D = query[1];
                if (!graph.count(C) || !graph.count(D)) {
                    results.push_back(-1.0);
                } else {
                    results.push_back(bfs(C, D, graph));
                }
            }
    
            return results;
        }
    
    private:
        double bfs(string src, string dest, unordered_map<string, vector<pair<string, double>>>& graph) {
            if (src == dest) return 1.0;
    
            queue<pair<string, double>> q;
            unordered_map<string, bool> visited;
            q.push({src, 1.0});
            visited[src] = true;
    
            while (!q.empty()) {
                auto [node, value] = q.front(); q.pop();
                
                for (auto& neighbor : graph[node]) {
                    string nextNode = neighbor.first;
                    double edgeWeight = neighbor.second;
                    
                    if (nextNode == dest) {
                        return value * edgeWeight;
                    }
                    
                    if (!visited[nextNode]) {
                        visited[nextNode] = true;
                        q.push({nextNode, value * edgeWeight});
                    }
                }
            }
            return -1.0; // No path found
        }
    };