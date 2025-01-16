import java.util.LinkedList;
import java.util.List;

class Snake{
    private LinkedList<int[]> snakeBody;
    private String snakeDirection;

    public Snake(List<int[]> snakeBody) {
        this.snakeBody = new LinkedList<>();

        for (int i = 0; i < snakeBody.size(); i++) {

            this.snakeBody.add(snakeBody.get(i));
        }
        this.snakeDirection = "";
    }

    public LinkedList<int[]> getSnakeBody(){
        return snakeBody;
    }

    public void addSnakeBody(int[] arr){
        snakeBody.addFirst(arr);
    }

    public void removeBodyFromLast(){
        snakeBody.removeLast();
    }
}
