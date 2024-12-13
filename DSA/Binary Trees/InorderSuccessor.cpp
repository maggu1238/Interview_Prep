void inorder(TreeNode<int>* root, TreeNode<int>* p, TreeNode<int>*& ans){
  if(!root){
    return;
  }
  inorder(root -> left, p, ans);
  if(ans == nullptr && root -> data > p -> data){
    ans = root;
    return;
  }
  inorder(root ->right, p, ans);
  
}
TreeNode<int>* InorderSuccessor(TreeNode<int>* root, TreeNode<int>* p)
{
  
  TreeNode<int>* temp = nullptr;
  inorder(root, p, temp);
  
    // Replace this placeholder return statement with your code
    return temp;
}