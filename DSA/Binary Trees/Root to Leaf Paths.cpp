/*Given a Binary Tree, you need to find all the possible paths from the root node to all the leaf nodes of the binary tree.*/
class Solution {
  public:
    void dfs(Node* root, vector<int>&temp, vector<vector<int>>& res){
        if( root == NULL)
            return;
        
        temp.push_back(root->data);
        if(root->left == NULL && root->right == NULL){
            res.push_back(temp);
            temp.pop_back();
            return;
        }
        
        dfs(root->left, temp, res);
        dfs(root->right, temp, res);
        temp.pop_back();
    }
    vector<vector<int>> Paths(Node* root) {
        // code here
        vector<vector<int>> res;
        vector<int> temp;
        
        dfs(root, temp, res);
        
        return res;
    }
};