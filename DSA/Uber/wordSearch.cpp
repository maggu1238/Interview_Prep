/*
Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
*/

class Solution {
    public:
        bool dfs(string word, int x, int y, string& temp, int index, vector<vector<char>>& board){
            if(temp == word){
                return true;
            }
    
            int x_dir[4] = {0,0,-1,1};
            int y_dir[4] = {-1,1,0,0};
    
    
            if(board[x][y] != word[index]){
                return false;
            }
    
            temp += word[index];
            board[x][y] = '.';
    

            // important step
            if(temp == word){
                return true;
            }
    
            for( int i =0; i <4; i++){
                int nx = x + x_dir[i];
                int ny = y + y_dir[i];
    
                if( nx >=0 && nx < board.size() && ny >=0 && ny < board[0].size() && board[nx][ny] != '.'){
                    
                    bool res = dfs(word, nx, ny, temp, index + 1, board);
                    if(res == true)
                        return true;
                    
                    
                }
            }
    
            temp.pop_back();
            board[x][y] = word[index];
    
            return false;
        }
    
        bool exist(vector<vector<char>>& board, string word) {
    
            string temp =  "";
    
            for( int i =0;  i < board.size(); i++){
                for( int  j = 0;  j  < board[0].size(); j++){
                    if(dfs (word, i, j, temp, 0, board)){
                        return true;
                    }
                }
            }
            return false;
        }   
    };