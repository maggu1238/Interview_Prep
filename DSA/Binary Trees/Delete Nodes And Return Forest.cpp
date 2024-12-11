
/*Given the root of a binary tree, each node in the tree has a distinct value.

After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).

Return the roots of the trees in the remaining forest. You may return the result in any order.*/

class Solution {
public:
    void dfs(TreeNode* root, unordered_map<int, pair<TreeNode*,TreeNode*> >& parentmp, TreeNode* parent){
    if(!root){
        return ;
    }
    
    parentmp[root -> val] = make_pair(parent, root);
    dfs(root ->left, parentmp, root);
    dfs(root -> right, parentmp, root);
    
    return;
    }
    vector<TreeNode*> delNodes(TreeNode* root, vector<int>& deleteNodes) {
        std::vector<TreeNode*> forest;
        unordered_map<int, pair<TreeNode*,TreeNode*> > parentmp;
        dfs(root, parentmp, nullptr);
        
        for(auto& it : deleteNodes){
        TreeNode* node =  parentmp[it].second;
        TreeNode* parent = parentmp[it].first;
        if(parent){
            if(parent -> right == node){
            parent ->right = nullptr;
            }
            else{
            parent ->left = nullptr;
            }
        }
        
        TreeNode* left = node ->left;
        TreeNode* right = node ->right;
        if(left){
            parentmp[left -> val].first = nullptr;
        }
        if(right){
            parentmp[right -> val].first =nullptr;
        }
        parentmp[node->val] = make_pair(node, node);
        }
        vector<int> result;
        
        for(auto& entry : parentmp){
        if(entry.second.first == nullptr){
            forest.push_back(entry.second.second);
        }
        }
        // Replace this placeholder return statement with your code
        return forest;
    }
};