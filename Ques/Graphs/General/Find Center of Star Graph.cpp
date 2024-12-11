/*Given an array edges where each element edges[i] = [ui, vi] represents an edge between nodes ui and vi in an undirected star graph, find the central node of this star graph.*/

/*A star graph is a graph where one central node is connected to every other node. This implies that a star graph with n nodes has exactly n - 1 edges.*/

int findCenter(const std::vector<std::vector<int>>& edges) {
    
    // Replace this placeholder return statement with your code
    int node1 = edges[0][0];
    int node2 = edges[0][1];
    
    if(node1 == edges[1][0] || node1 == edges[1][1])
      return node1;
    else if(node2 == edges[1][0] || node2 == edges[1][1])
      return node2;
    return -1;
}