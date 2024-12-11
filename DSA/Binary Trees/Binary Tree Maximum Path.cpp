/*A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.

The path sum of a path is the sum of the node's values in the path.

Given the root of a binary tree, return the maximum path sum of any non-empty path.*/
int sum(TreeNode<int>* root, int& maxi){
  if(!root)
    return 0;
  int leftPathSum = sum(root ->left, maxi);
  int rightPathSum = sum(root -> right, maxi);
  
  int a = max(leftPathSum, rightPathSum) + root-> data;
  int b = max(a, root->data);
  int c = max(b, leftPathSum + rightPathSum + root -> data);
  maxi = max(maxi, c);
  
  return b;
}

int MaxPathSum(TreeNode<int>* root){
    
    int maxi = INT_MIN;
    // Replace this placeholder return statement with your code
    sum(root, maxi);
    return maxi;
}