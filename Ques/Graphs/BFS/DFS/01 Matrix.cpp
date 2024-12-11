/*Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.

The distance between two adjacent cells is 1.*/

class Solution {
public:
    vector<vector<int>> updateMatrix(vector<vector<int>>& mat) {
        int rows = mat.size();
        int cols = mat[0].size();
        queue<pair<int, int>> q;

        for( int i = 0; i < rows; i++){
            for( int j = 0; j < cols; j++){
                if( mat[i][j] == 0){
                    q.push(make_pair(i,j));
                }
            }
        }
        int x[4] = {0,0,1,-1};
        int y[4] = {1,-1,0,0};
        int steps = 1;
        int size = q.size();
        while(!q.empty()){
            int X = q.front().first;
            int Y = q.front().second;
            q.pop();
            size--;
            for( int i = 0; i < 4; i++){
                int newX = X + x[i];
                int newY = Y + y[i];
                if(newX >= 0 && newX < rows && newY >= 0 && newY < cols
                && mat[newX][newY] > 0){
                    mat[newX][newY] = -steps;
                    q.push(make_pair(newX, newY));
                }
            }
            if(size == 0){
                steps++;
                size =  q.size();
            }
        }

        for( int i =0; i< rows; i++){
            for( int j = 0; j < cols; j++){
                if(mat[i][j] < 0){
                    mat[i][j] = -mat[i][j];
                }
            }
        }
        return mat;
    }
};