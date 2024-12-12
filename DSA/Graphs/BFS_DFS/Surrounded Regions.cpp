/*You are given an m x n matrix board containing letters 'X' and 'O', capture regions that are surrounded:

Connect: A cell is connected to adjacent cells horizontally or vertically.
Region: To form a region connect every 'O' cell.
Surround: The region is surrounded with 'X' cells if you can connect the region with 'X' cells and none of the region cells are on the edge of the board.
A surrounded region is captured by replacing all 'O's with 'X's in the input matrix board.*/

class Solution {
public:
    void solve(vector<vector<char>>& board) {
        queue<pair<int,int>> q;
        int rows =  board.size();
        int cols = board[0].size();

        for( int i = 0; i < rows; i++){
            if(board[i][0] == 'O'){
                board[i][0] = 'c';
                q.push(std::make_pair(i, 0));
            }

            if(board[i][cols-1] == 'O'){
                q.push(std::make_pair(i, cols-1));
                board[i][cols-1] = 'c';
            }
        }

        for( int i = 0; i < cols; i++){
            if(board[0][i] == 'O'){
                q.push(std::make_pair(0, i));
                board[0][i] = 'c';
            }

            if(board[rows-1][i] == 'O'){
                q.push(std::make_pair(rows-1, i));
                board[rows-1][i] = 'c';
            }
        }

        int x[4] = {0,0,1,-1};
        int y[4] = {1,-1,0,0};

        while(!q.empty()){
            pair<int,int> p = q.front();
            int X = p.first;
            int Y = p.second;

            q.pop();
            for( int i = 0; i < 4; i++){
                int new_x = X + x[i];
                int new_y = Y + y[i];
                if( new_x >=0 && new_x < rows && new_y >=0 && new_y < cols
                && board[new_x][new_y] == 'O'){
                    board[new_x][new_y] = 'c';
                    q.push(make_pair(new_x,new_y));
                }
            }
        }

        for( int i=0; i< rows;i++){
            for( int j = 0; j < cols; j++){
                if(board[i][j] != 'c'){
                    board[i][j] = 'X';
                }
                else{
                    board[i][j] = 'O';
                }
            }
        }
    }
};