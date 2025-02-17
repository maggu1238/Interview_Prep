/*
Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
*/

class Solution {
    public:
        void dfs(int i, int j, vector<vector<char>>& grid){
            
            int m = grid.size();
            int n = grid[0].size();
            
            if(i == -1 || j == -1 || i == m || j == n)
                return;
            
            if(grid[i][j] !='1')
                return;
            
            grid[i][j] = '2';
            dfs(i+1, j, grid);
            dfs(i-1, j, grid);
            dfs(i, j+1, grid);
            dfs(i, j-1, grid);        
        }
        int numIslands(vector<vector<char>>& grid) {
            
            int m = grid.size();
            int n = grid[0].size();
            
            int count =0 ;
            for( int i = 0 ; i < m; i++){
                for( int j = 0; j < n; j++){
                    if(grid[i][j] == '1'){
                        dfs(i, j,grid);
                        count++;
                    }
                }
            }
            
            return count;
        }
    };