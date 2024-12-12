/*You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. Ingredients to a recipe may need to be created from other recipes, i.e., ingredients[i] may contain a string that is in recipes.

You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.

Return a list of all the recipes that you can create. You may return the answer in any order.

Note that two recipes may contain each other in their ingredients.*/

class Solution {
public:
    vector<string> findAllRecipes(vector<string>& recipes, vector<vector<string>>& ingredients, vector<string>& supplies) {
        unordered_map<string,int> indegree;
        unordered_map<string,vector<string>> adj;
        int n = ingredients.size();
        for(int i=0;i<n;i++){
            for(auto s : ingredients[i]){
                adj[s].push_back(recipes[i]);
                indegree[recipes[i]]++;
            }
        }
        //only the recieps[i]  have the positive indegrees
        //after removing edges if the indegree[recipes[i]] == 0
        //means current recipes[i] can be preformed by the present nodes



        //something before something always think about the toposorting once 
        //ingredients of the supplies are 0;
        queue<string> q;
        for(auto it : supplies){
            q.push(it);
        }
        vector<string> ans;
        while(q.empty() == false){
            string  node = q.front();
            q.pop();
            for(auto adjnode : adj[node]){
                indegree[adjnode]--;
                if(indegree[adjnode] == 0){
                    q.push(adjnode);
                }
            }
        }
        //if indegree[recipes[i]] == 0 ...means i made it to the final 
        for(string r : recipes){
            if(indegree[r] == 0){
                ans.push_back(r);
            }
        }
        return ans;
    }
};