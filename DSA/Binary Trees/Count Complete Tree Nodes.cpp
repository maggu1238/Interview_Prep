/*Given the root of a complete binary tree, return the number of the nodes in the tree.

According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

Design an algorithm that runs in less than O(n) time complexity.*/
class Solution {
public:
    int left_depth(TreeNode* root)
    {
        int count=0;
        while(root)
        {
            root = root->left;
            count++;
        }
        return count;
    }
    int right_depth(TreeNode* root)
    {
        int count=0;
        while(root)
        {
            root= root->right;
            count++;
        }
        return count;
    }
    int count(int n)
    {
        return pow(2,n)-1;
    }
    int countNodes(TreeNode* root) {
        
        
        int l = left_depth(root);
        int r = right_depth(root);
        if( l!=r)
            return 1 + countNodes(root->right)+countNodes(root->left);
        
        return count(l);
    
    }
};