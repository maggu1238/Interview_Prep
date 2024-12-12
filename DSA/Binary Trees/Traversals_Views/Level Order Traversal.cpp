/*Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).*/
class Solution {
public:
    vector<vector<int>> levelOrder(TreeNode* root) {
        int size = 0;
        vector<vector<int> > result;
        if( root ==NULL)
        return result;
        queue<TreeNode*> q;
        q.push(root);
        size++;
        
        while(!q.empty()){
            vector<int> temp;
            for( int i =0; i < size; i++){
                TreeNode* node = q.front();
                q.pop();
                temp.push_back(node -> val);
                if(node -> left != NULL){
                    q.push(node->left);
                }
                if(node -> right != NULL){
                    q.push(node -> right);
                }
            }
            size = q.size();
            result.push_back(temp);
        }
        return result;
    }
};