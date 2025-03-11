/*
You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight.
 Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.

For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
*/

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
    
           // we do a post order so that we know that the path we are exploring is not a dead end
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