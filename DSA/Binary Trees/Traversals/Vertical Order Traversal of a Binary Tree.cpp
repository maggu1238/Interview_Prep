/*Given the root of a binary tree, calculate the vertical order traversal of the binary tree.

For each node at position (row, col), its left and right children will be at positions (row + 1, col - 1) and (row + 1, col + 1) respectively. The root of the tree is at (0, 0).

The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each column index starting from the leftmost column and ending on the rightmost column. There may be multiple nodes in the same row and same column. In such a case, sort these nodes by their values.

Return the vertical order traversal of the binary tree.*/

 void dfs(TreeNode* root, unordered_map<int, vector<multiset<int>> >& mp, 
            int row, int col, int& minCol, int& maxCol, int& maxRow) {
    if(root == NULL)
        return;
    
    if(mp.find(col) == mp.end()){
        vector<multiset<int>> v(1000);
        mp[col] = v;
    }
    mp[col][row].insert(root->val);
    if(maxCol < col)
        maxCol = col;
    if(minCol > col)
        minCol = col;
    if(maxRow < row)
        maxRow = row;
    dfs(root -> left, mp, row + 1, col - 1, minCol, maxCol, maxRow);
    dfs(root -> right, mp, row + 1, col + 1, minCol, maxCol, maxRow);
    return;
 }
class Solution {
public:
    vector<vector<int>> verticalTraversal(TreeNode* root) {
        unordered_map<int, vector<multiset<int>> > mp;
        vector<vector<int>> result;

        int row = 0;
        int col = 0;
        int minCol = INT_MAX;
        int maxCol = INT_MIN;
        int maxRow = INT_MIN;

        dfs(root, mp, row, col, minCol, maxCol, maxRow);

        for( int i =minCol; i <= maxCol; i++){
            vector<int> temp;

            if(mp[i].size() != 0){
                int size = mp[i].size();
                for( int j = 0; j < size; j++){
                    multiset<int> s = mp[i][j];
                    for (int element : s) {
                        temp.push_back(element);
                    }
                }
            }
            result.push_back(temp);
        }

        return result;
        
    }
};