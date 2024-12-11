/*Given a binary tree, determine if it is 
height-balanced*/

class Solution {
public:
    int height(TreeNode* root){
        if(root ==NULL)
            return 0;
        return 1 + max(height(root ->left), height(root->right));
    }

    bool isBalanced(TreeNode* root) {

        if(root == NULL)
            return true;
        if(abs(height(root->left) - height(root->right)) <= 1 && isBalanced(root->right) && isBalanced(root->left)){
            return true;
        }
        return false;
    }
};