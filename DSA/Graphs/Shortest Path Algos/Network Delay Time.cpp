/*You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.

We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.*/
class Solution {
public:
    int networkDelayTime(vector<vector<int>>& times, int n, int k) {
        unordered_map<int,vector<pair<int,int>>> adj;
        int total_nodes = n;
        for( int i =0; i < times.size(); i++){
            int s = times[i][0];
            int d = times[i][1];
            int w = times[i][2];
            adj[s].push_back(make_pair(d,w));
        }
        priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
        vector<int> minTime(n+1,INT_MAX);
        int res = INT_MIN;

        pq.push(make_pair(0,k));
        minTime[k] = 0;
        n--;
        while(!pq.empty()){
            int node = pq.top().second;
            int dist = pq.top().first;
            pq.pop();
            for( int i =0; i < adj[node].size(); i++){
                int child = adj[node][i].first;
                int weight = adj[node][i].second;

                if(minTime[child] > dist + weight){
                    if(minTime[child] == INT_MAX)
                        n--;
                    minTime[child] = dist + weight;

                    pq.push(make_pair(minTime[child], child));
                }
            }
        }
        if(n > 0)
            return -1;

        for( int i = 1; i<=total_nodes; i++){
            res = max(minTime[i], res);
        }
        return res;

    }
};