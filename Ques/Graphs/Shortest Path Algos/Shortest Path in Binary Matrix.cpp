/*Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1.

A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:

All the visited cells of the path are 0.
All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
The length of a clear path is the number of visited cells of this path.*/
class Solution {
public:
    int shortestPathBinaryMatrix(vector<vector<int>>& grid) {
        queue<pair<int,int>> q;
        q.push(make_pair(0,0));
        int m = grid.size();

        int xdir[8] = {0,0,1,-1,-1,-1,1,1};
        int ydir[8] = {1,-1,0,0,-1,1,-1,1};
        if(grid[0][0] == 1 || grid[m-1][m-1] == 1)
            return -1;
        int size = 1;
        grid[0][0]  =  1;
        while(!q.empty()){
            pair<int,int> p = q.front();
            q.pop();
            if(p.first == m - 1 && p.second == m - 1)
                return grid[p.first][p.second];
            for( int i = 0; i < 8; i++){
                int x = p.first + xdir[i];
                int y = p.second + ydir[i];

                if(x >= 0 && x < m && y >= 0 && y < m && grid[x][y] == 0){
                    q.push(make_pair(x,y));
                    grid[x][y] = grid[p.first][p.second] + 1;
                }
            }               
        }
        return -1;
    }
};