/*Given the root of a binary tree, the task is to flatten the tree into a linked list using the same TreeNode class. The left child pointer of each node in the linked list should always be NULL, and the right child pointer should point to the next node in the linked list. The nodes in the linked list should be in the same order as that of the preorder traversal of the given binary tree.*/

TreeNode<int> *FlattenTree(TreeNode<int> *root){

    // Replace this placeholder return statement with your code
    
    TreeNode<int> * temp = root;
  
    while(root){
      TreeNode<int> * leftNode = root -> left;
      if(leftNode){
        while(leftNode -> right){
          leftNode = leftNode -> right;
        }
        leftNode ->right = root ->right;
        root -> right = root -> left;
        root ->left = nullptr;  
      }
      root  = root -> right;
    }
    
    
    return temp;
}