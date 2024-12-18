/*You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.

A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.

Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.*/
class Solution {
public:
    int numEnclaves(vector<vector<int>>& grid) {
        queue<pair<int,int>> q;
        int rows = grid.size();
        int cols = grid[0].size();

        for( int i = 0; i < rows; i++){
            if(grid[i][0] == 1){
                grid[i][0] = -1;
                q.push(make_pair(i,0));
            }
            if(grid[i][cols-1] == 1){
                grid[i][cols-1] = -1;
                q.push(make_pair(i,cols-1));
            }
        }

        for( int i =0; i < cols; i++){
            if(grid[0][i] == 1){
                grid[0][i] = -1;
                q.push(make_pair(0,i));
            }
            if(grid[rows-1][i] == 1){
                grid[rows-1][i] = -1;
                q.push(make_pair(rows-1,i));
            }
        }
        int x[4] = {0,0,1,-1};
        int y[4] = {1,-1,0,0};

        while(!q.empty()){
            pair<int,int> node = q.front();
            q.pop();
            for( int i = 0; i < 4; i++){
                int X = node.first + x[i];
                int Y = node.second + y[i];
                if(X >= 0 && X< rows && Y >= 0 && Y < cols && grid[X][Y] == 1 ){
                    grid[X][Y] = -1;
                    q.push(make_pair(X,Y));
                }
            }
        }
        int res = 0;
        for( int i=0 ; i < rows; i++){
            for( int j = 0; j < cols; j++){
                if(grid[i][j] == 1){
                    res++;
                }
            }
        }
        return res;
    }
};