/*Given n as the number of nodes and an array of the edges of a graph, find out if the graph is a valid tree. The nodes of the graph are labeled from 0 to n−1, and edges[i]=[x,y]
edges[i]=[x,y] represents an undirected edge connecting the nodes x and y of the graph.

A graph is a valid tree when all the nodes are connected and there is no cycle between them.*/

bool ValidTree(int n, std::vector<std::vector<int>>& edges) {
    // Check if n - 1 edges exists
    // edges > n- 1 -> cycle
    // edges < n-1 -> not connected
    if (edges.size() != n - 1) {
        return false;
    }

    std::vector<std::vector<int>> adjacency(n);

    // Populate adjacency with all the connected nodes
    for (const auto& edge : edges) {
        int x = edge[0];
        int y = edge[1];
        adjacency[x].push_back(y);
        adjacency[y].push_back(x);
    }

    std::unordered_set<int> visited;
    std::stack<int> stack;
    visited.insert(0);
    stack.push(0);

    while (!stack.empty()) {
        int node = stack.top();
        stack.pop();

        // Iterate over the neighbors of the popped node
        for (int neighbor : adjacency[node]) {
            if (visited.find(neighbor) == visited.end()) {

                // Add a neighbor in visited set and stack if it doesn't already exist in the set
                visited.insert(neighbor);
                stack.push(neighbor);
            }
        }
    }

    return visited.size() == n;
}