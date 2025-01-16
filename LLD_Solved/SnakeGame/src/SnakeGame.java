import java.util.LinkedList;

public class SnakeGame {
    private int boardWidth;
    private int boardHeight;
    private LinkedList<int[]> snake; // Snake represented as a list of coordinates
    private int[] direction; // Current direction of movement (dx, dy)
    private int movesCount; // Counter for moves
    private boolean isGameOver;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.snake = new LinkedList<>();
        this.snake.add(new int[]{0, 0});
        this.snake.add(new int[]{0, 1});
        this.snake.add(new int[]{0, 2});
        this.direction = new int[]{0, 1}; // Initial direction: right
        this.movesCount = 0;
        this.isGameOver = false;
    }

    public void moveSnake(String snakeDirection) {
        if (isGameOver) return;

        // Update direction based on input
        switch (snakeDirection.toLowerCase()) {
            case "up":    direction = new int[]{-1, 0}; break;
            case "down":  direction = new int[]{1, 0}; break;
            case "left":  direction = new int[]{0, -1}; break;
            case "right": direction = new int[]{0, 1}; break;
        }

        // Calculate new head position
        int[] currentHead = snake.getFirst();
        int newHeadRow = currentHead[0] + direction[0];
        int newHeadCol = currentHead[1] + direction[1];

        // Check for collisions
        if (newHeadRow < 0 || newHeadRow >= boardHeight || newHeadCol < 0 || newHeadCol >= boardWidth) {
            isGameOver = true;
            return;
        }

        // Check if the new head position collides with the snake's body
        for (int[] segment : snake) {
            if (segment[0] == newHeadRow && segment[1] == newHeadCol) {
                isGameOver = true;
                return;
            }
        }

        // Add new head to the snake
        snake.addFirst(new int[]{newHeadRow, newHeadCol});
        movesCount++;

        // Remove the tail unless the snake grows every 5 moves
        if (movesCount % 5 != 0) {
            snake.removeLast();
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void printSnake() {
        System.out.print("Snake: ");
        for (int[] segment : snake) {
            System.out.print("[" + segment[0] + "," + segment[1] + "] ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SnakeGame game = new SnakeGame(10, 10);

        game.moveSnake("right");
        game.printSnake();

        game.moveSnake("down");
        game.printSnake();

        game.moveSnake("down");
        game.printSnake();

        System.out.println("Game Over: " + game.isGameOver());
    }
}
