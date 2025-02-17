/*You are given a root of a binary tree that has n number of nodes. You have to return the right-side view in the form of a list.

A right-side view of a binary tree is the data of the nodes that are visible when the tree is viewed from the right side.*/

void dfs(TreeNode<int>* root, int level, vector<int>& list){
  if(!root)
    return;
  if(level == list.size()){
    list.push_back(root->data);
  }
  dfs(root ->right, level +1, list);
  dfs(root ->left, level + 1, list);
  return;
}

// Function to get the right side view of a binary tree
std::vector<int> RightSideView(TreeNode<int>* root) {

    // Replace this placeholder return statement with your code
    
    vector<int> res;
    dfs(root, 0, res);
    
    return res;
}