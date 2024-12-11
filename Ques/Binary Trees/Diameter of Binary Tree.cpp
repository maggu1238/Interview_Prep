/*Given a binary tree, you need to compute the length of the tree’s diameter. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.*/

/*Note: The length of the path between two nodes is represented by the number of edges between them.*/
int height (TreeNode<int> *root, int& diameter){
  
  if(root == nullptr)
    return 0;
  int lh = height(root ->left, diameter);
  int rh = height(root ->right, diameter);
  diameter = max(lh + rh, diameter);
  return max(lh,rh) +  1;
}
// DiameterOfBinaryTree returns the diameter of tree
int DiameterOfBinaryTree(TreeNode<int> *root)
{
    int diameter = 0;
    height(root, diameter);
    
    
    // Replace this placeholder return statement with your code
    return diameter;
}