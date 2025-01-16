import java.util.List;

public class SnakeGame1 implements SnakeGame{

    private boolean isGameOver;
    private int[] direction;
    private Snake snake;
    private GameBoard gameBoard;
    private int moveCounts;

    public  SnakeGame1(int boardheight, int boardWidth, List<int[]> snakeBody){
            this.gameBoard = new GameBoard(boardheight, boardWidth);
            this.snake = new Snake(snakeBody);
            this.isGameOver = false;
            this.moveCounts = 0;
    }

    @Override
    public void moveSnake(String snakeDirection) {
        if (isGameOver){
            return;
        }

        switch (snakeDirection){
            case "up":
                direction = new int[]{-1,0};
                break;
            case "down":
                direction = new int[]{1,0};
                break;
            case "left":
                direction = new int[]{0,-1};
                break;
            case "right":
                direction = new int[]{0,1};
                break;
        }

        int[] currentHead = this.snake.getSnakeBody().getFirst();

        int newHeadRow = currentHead[0] + direction[0];
        int newHeadCol = currentHead[1] + direction[1];

        if(newHeadRow < 0 ){
            newHeadRow = gameBoard.getBoardWidth() - 1;
        }
        else if(newHeadRow >= gameBoard.getBoardheight()){
            newHeadRow = 0;
        }

        if(newHeadCol < 0){
            newHeadCol = gameBoard.getBoardheight() - 1;
        }
        else if(newHeadCol >= gameBoard.getBoardWidth()){
            newHeadCol = 0;
        }

        for(int[] segment : this.snake.getSnakeBody()){
           // System.out.println(segment[0] + " " + newHeadRow +" "+ segment[1] + " "+newHeadCol);
            if( segment[0] == newHeadRow && segment[1] == newHeadCol){
                isGameOver = true;
                return;
            }
        }

        this.snake.addSnakeBody(new int[]{newHeadRow, newHeadCol});
        moveCounts++;

        if(moveCounts %5 != 0){
            this.snake.removeBodyFromLast();
        }
    }

    @Override
    public boolean isGameOver() {
        return isGameOver;
    }
}