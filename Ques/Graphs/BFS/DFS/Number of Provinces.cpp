/*There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.*/

class Solution {
public:

    void dfs(int node, vector<int>& visited, vector<vector<int>>& isConnected){
        if( visited[node] == 1){
            return;
        }

        visited[node] = 1;
        for( int i = 0; i < isConnected[node].size(); i++){
            if( isConnected[node][i] == 1 && node != i){
                dfs(i, visited, isConnected);
            }
        }
    }

    void bfs(int node, vector<int>& visited, vector<vector<int>> isConnected){
        queue<int> q;
        q.push(node);

        while(!q.empty()){
            int node1 = q.front();
            visited[node1] = 1;
            q.pop();
            for( int i = 0; i < isConnected[node1].size() ; i++){
                if( isConnected[node1][i] == 1 && i != node1 && visited[i] == 0){
                    q.push(i);
                    ////cout<<i <<" ";
                }
            }
            //cout<<"\n";
        }
        return ;
    }


    int findCircleNum(vector<vector<int>>& isConnected) {
        int n = isConnected.size();

        vector<int> visited(n,0);
        int provinces =0;

        for( int i = 0; i <n ; i++){
            if(visited[i] == 0){
                provinces++;               
                bfs(i, visited, isConnected);
            }
        }

        return provinces;
    }
};