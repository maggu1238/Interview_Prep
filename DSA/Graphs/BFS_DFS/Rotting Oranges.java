/*You are given an m x n grid where each cell can have one of three values:

0 representing an empty cell,
1 representing a fresh orange, or
2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.*/


class Solution {
public:
    int orangesRotting(vector<vector<int>>& grid) {
        queue<pair<int,int>> q;
        int freshOranges = 0;
        for(int i =0;i < grid.size(); i++){
            for(int j =0; j < grid[0].size(); j++){
                if( grid[i][j] == 2){
                    q.push(std::make_pair(i,j));
                }
                if(grid[i][j] == 1){
                    freshOranges++;
                }
            }
        }
        int size = q.size();

        int x[4] = {0,0,1,-1};
        int y[4] = {1,-1,0,0};
        int steps = 0; 

        while(!q.empty()){
            pair<int,int> p = q.front();
           // cout<<p.first<<" "<<p.second<<"\n";

            q.pop();
            size--;

            for( int i = 0; i < 4; i++){
                int newX = p.first + x[i];
                int newY = p.second + y[i];

                if(newX >= 0 && newX < grid.size() && newY >= 0 && newY < grid[0].size() 
                && grid[newX][newY] == 1){
                    freshOranges--;
                    grid[newX][newY] = 2;
                   // cout<<grid[newX][newY]<<"\n";
                    q.push(std::make_pair(newX,newY));
                }
            }

            if(size == 0 && q.size() != 0){
                steps++;
                size = q.size();
            }
        }
        if(freshOranges > 0)
            return -1;

        return steps;
    }
};
