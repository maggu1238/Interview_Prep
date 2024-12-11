/*Given the root of a binary tree, return the preorder traversal of its nodes' values.*/
class Solution {
public:
    void preOrder(TreeNode* root, vector<int>& result){
        if(root == NULL){
            return;
        }

        result.push_back(root-> val);
        preOrder(root -> left, result);
        preOrder(root -> right, result);
        return;
    }
    vector<int> preorderTraversal(TreeNode* root) {
        queue<int> q;
        q.push(root);
        int size = 0;
        
        vector<int> result;
        preOrder(root, result);

        Q

        return result;
    }
};