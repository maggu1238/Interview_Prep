/*You are given an Undirected Graph having unit weight of the edges, find the shortest path from src 
to all the vertex and if it is unreachable to reach any vertex, then return -1 for that vertex.*/

class Solution {
  public:
    vector<int> shortestPath(vector<vector<int>>& edges, int N,int M, int src){
        // code here
        vector<vector<int>> adj(N);
        vector<bool> visited(N,false);
        vector<int> pathlength(N,-1);
        
        for( int i =0 ; i < edges.size(); i++){
            vector<int> edge  = edges[i];
            adj[edge[0]].push_back(edge[1]);
            adj[edge[1]].push_back(edge[0]);
        }
        
        queue<int> q;
        q.push(src);
        visited[src] = true;
        pathlength[src]  = 0;
        
        int length = 0;
        int size  = q.size();
        
        while(!q.empty()){
            int node = q.front();
            q.pop();
            pathlength[node] = length;
            size--;
            for( int i =0; i < adj[node].size(); i++){
                int child = adj[node][i];
                if(visited[child] == false){
                    q.push(child);
                    visited[child] = true;
                }
                
            }
            if( size == 0){
                length++;
                size  = q.size();
            }
        }
        
        return pathlength;
    }
};