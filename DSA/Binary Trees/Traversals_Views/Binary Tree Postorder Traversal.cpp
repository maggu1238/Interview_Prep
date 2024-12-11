/*Given the root of a binary tree, return the postorder traversal of its nodes' values.*/
class Solution {
public:
    void postOrder(TreeNode* root, vector<int>& result){
        if( root == NULL){
            return;
        }
        postOrder(root->left, result);
        postOrder(root-> right, result);
        result.push_back(root->val);
        return;
    }
    vector<int> postorderTraversal(TreeNode* root) {
        vector<int> result;
        postOrder(root, result);
        return result;
    }
};