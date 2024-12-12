/*You are given an image represented by an m x n grid of integers image, where image[i][j] represents the pixel value of the image. You are also given three integers sr, sc, and color. Your task is to perform a flood fill on the image starting from the pixel image[sr][sc].

To perform a flood fill:

Begin with the starting pixel and change its color to color.
Perform the same process for each pixel that is directly adjacent (pixels that share a side with the original pixel, either horizontally or vertically) and shares the same color as the starting pixel.
Keep repeating this process by checking neighboring pixels of the updated pixels and modifying their color if it matches the original color of the starting pixel.
The process stops when there are no more adjacent pixels of the original color to update.
Return the modified image after performing the flood fill.*/

class Solution {
public:
    void dfs(int sr, int sc, vector<vector<int>>& image, int color){
        if(sr < 0 || sr >= image.size() || sc < 0 || sc >= image[0].size()){
            return;
        }

        if(image[sr][sc] == -1){
            return;
        }

        if (image[sr][sc] != color){
            return;
        }

        image[sr][sc] = -1;

        int x[4] = {0, 0, 1, -1};
        int y[4] = {1, -1, 0, 0};

        for(int i = 0; i < 4; i++ ){
            int x_co = sr + x[i];
            int y_co = sc + y[i];
            dfs(x_co, y_co, image, color);
        }

        return;
    }

    vector<vector<int>> floodFill(vector<vector<int>>& image, int sr, int sc, int color) {
        
        dfs(sr,sc,image, image[sr][sc]);

        for( int i = 0; i < image.size(); i++){
            for( int j = 0; j < image[0].size(); j++){
                if( image[i][j] == -1){
                    image[i][j] = color;
                }
            }
        }

        return image;
    }
};