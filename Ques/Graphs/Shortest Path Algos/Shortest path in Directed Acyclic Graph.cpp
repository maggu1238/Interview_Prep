/*Given a Directed Acyclic Graph of V vertices from 0 to n-1 and a 2D Integer array(or vector) edges[ ][ ] of length E, where there is a directed edge from edge[i][0] to edge[i][1] with a distance of edge[i][2] for all i.

Find the shortest path from src(0) vertex to all the vertices and if it is impossible to reach any vertex, then return -1 for that vertex.*/

class Solution {
  public:
    void dfs(int node, vector<bool>& visited, vector<int>& minDist, vector<vector<pair<int,int>>> adj){
        
        
        for( int i = 0; i < adj[node].size(); i++){
            int cost = adj[node][i].second;
            int childNode = adj[node][i].first;
            if(visited[childNode] == false){
                minDist[childNode] = minDist[node] + cost;
                visited[childNode] = true;
            }   
            else if(minDist[childNode] > minDist[node] + cost){
                minDist[childNode] = minDist[node] + cost;     
            }
            else {
                continue;
            }
            dfs(childNode, visited, minDist, adj);
        }
        return;
    }
    
    vector<int> shortestPath(int V, int E, vector<vector<int>>& edges) {
        // code here
        vector<vector<pair<int,int>>> adj(V);
        
        for( int i =0; i < edges.size(); i++){
            adj[edges[i][0]].push_back(make_pair(edges[i][1], edges[i][2]));
        }
        vector<bool> visited(V,false);
        vector<int> minDist(V,-1);
        minDist[0] = 0;
        visited[0] = true;
        dfs(0, visited, minDist, adj);
        
        return minDist;
    }
};