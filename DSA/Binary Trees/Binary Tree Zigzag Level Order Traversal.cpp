/*Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).*/
class Solution {
public:
    vector<vector<int>> zigzagLevelOrder(TreeNode* root) {
        int size = 0;
        vector<vector<int>> res;

        queue<TreeNode*> q;
        if( !root)
            return res;
        q.push(root);
        size =1;
        bool rev = false;
        while(!q.empty()){
            vector<int> temp;
            for( int i = 0; i < size; i++){
                TreeNode* node = q.front();
                q.pop();
                if(node -> left){
                    q.push(node -> left);
                }
                if(node -> right){
                    q.push(node -> right);
                }
                temp.push_back(node -> val);
            }
            if( rev){
                reverse(temp.begin(), temp.end());
            }
            rev = !rev;
            res.push_back(temp);
            size = q.size();
        }
        return res;
        
    }
};