/*Given a list of airline tickets where tickets[i] = [fromi, toi] represent a departure airport and an arrival airport of a single flight, reconstruct the itinerary in the correct order and return it.

The person who owns these tickets always starts their journey from "JFK". Therefore, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should prioritize the one with the smallest lexical order when considering a single string.

 You may assume all tickets form at least one valid itinerary. You must use all the tickets exactly once.*/

 class Solution {
public:
    void dfs(string node, vector<string>& res, map<string, vector<string>>& adjList)
    {   
     // cout<<node<<" ";
       while(adjList[node].size())
       {
           string neighbour = *adjList[node].begin();
           adjList[node].erase( adjList[node].begin() );
           dfs(neighbour, res, adjList);
           
       }
       res.push_back(node);
       return;
    }
    vector<string> findItinerary(vector<vector<string>>& tickets) {
        vector<string > res;
        
        map< string, vector<string>> adjList;
        map< string, bool> visited;
        int N = tickets.size();
        for(int i = 0; i < tickets.size(); i++)
        {
            vector<string> ticket = tickets[i];
            string src = ticket[0];
            string dst = ticket[1];
            
            adjList[src].push_back(dst);
            
        }

        for(auto& entry : adjList){

            std::sort(entry.second.begin(), entry.second.end());
        }
        
        dfs("JFK", res, adjList) ;
        
        reverse( res.begin(), res.end() );
        return res;
    }
};