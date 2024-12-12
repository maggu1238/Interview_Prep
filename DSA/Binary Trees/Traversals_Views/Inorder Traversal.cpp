/*Given the root of a binary tree, return the inorder traversal of its nodes' values.*/
class Solution {
public:
    void inOrder(TreeNode* root, vector<int>& result){
        if( root  == NULL){
            return;
        }
        
        inOrder(root->left, result);
        result.push_back(root -> val);
        inOrder(root -> right, result);
        return;
    }
    vector<int> inorderTraversal(TreeNode* root) {
        vector<int> result;
        inOrder(root, result);
        return result;
    }
};