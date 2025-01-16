import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<int[]> arr = new ArrayList<>();
        arr.add(new int[]{0,0});
        arr.add(new int[]{0,1});
        arr.add(new int[]{0,2});

        SnakeGame1 snakeGame1 = new SnakeGame1(4,4, arr);

        snakeGame1.moveSnake("left");
        System.out.println("is Game over -> " + snakeGame1.isGameOver());

        snakeGame1.moveSnake("left");
        snakeGame1.moveSnake("left");
        System.out.println("is Game over -> " + snakeGame1.isGameOver());
        snakeGame1.moveSnake("down");
        snakeGame1.moveSnake("down");
        snakeGame1.moveSnake("down");
        snakeGame1.moveSnake("down");
        snakeGame1.moveSnake("down");
        System.out.println("is Game over -> " + snakeGame1.isGameOver());


        snakeGame1.moveSnake("right");
        snakeGame1.moveSnake("up");
        snakeGame1.moveSnake("left");

        System.out.println("is Game over -> " + snakeGame1.isGameOver());




    }
}