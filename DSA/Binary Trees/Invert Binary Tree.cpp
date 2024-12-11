/*Given the root node of a binary tree, transform the tree by swapping each node’s left and right subtrees, thus creating a mirror image of the original tree. Return the root of the transformed tree.*/


TreeNode<int>* MirrorBinaryTree(TreeNode<int>* root){

    // Replace this placeholder return statement with your code
    if(!root){
      return nullptr;
    }
    root->left = MirrorBinaryTree(root ->left);
    root-> right = MirrorBinaryTree(root ->right);
    TreeNode<int>* temp = root->left;
    root -> left = root -> right;
    root -> right = temp;
    
    return root;
}