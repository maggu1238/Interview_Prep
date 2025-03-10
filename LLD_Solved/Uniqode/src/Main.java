import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game2048{

    public int[][] board;
    private Random random = new Random();
    private int boardSize;

    public Game2048(int size){
        board = new int[size][size];
        boardSize = size;
        addNewTile(size);
        addNewTile(size);
        printBoard();
    }

    private void addNewTile(int size) {
        List<int[]> emptyCells = new ArrayList<>();
        for( int r=0; r<size; r++){
            for(int c=0; c < size; c++){
                if(board[r][c] == 0){
                    emptyCells.add(new int[]{r,c});
                }
            }
        }

        if(!emptyCells.isEmpty()){
            int[] pos = emptyCells.get(random.nextInt(emptyCells.size()));
            board[pos[0]][pos[1]] = 2;
        }

//        printBoard();
    }

    // win //GameOver // continue
    public String checkStatus(){
        String result = "continue";
        if(isGameWon()){
            result = "won";
        }

        if(!movePossible()){
            result = "gameOver";
        }
        return result;

    }

    public boolean isGameWon(){
        for(int r= 0; r < boardSize; r++){
            for( int c = 0; c < boardSize; c++){
                if(board[r][c] == 2048){
                    return true;
                }
            }
        }
        return false;
    }

    // check if we can made a move on any row


    private boolean movePossible(){
        boolean moved = false;
        for( int r =0; r  < boardSize; r++){
            int[] newRow = compress(board[r]);
            newRow = merge(newRow);
            newRow = compress(newRow);

            if(!equalRows(newRow, board[r])){
                moved = true;
            }
        }

        return moved;
    }

//
//    abstract Directtion  {{}
//    left(mvove()), rightmove()
//
//            Directoi


    private boolean equalRows(int[]row1, int[]row2){
        for(int i=0; i < boardSize; i++){
            if(row1[i] != row2[i]){
                return  false;
            }
        }

        return  true;
    }

    public void moveLeft(){
        boolean moved = false;
        for( int r =0; r  < boardSize; r++){
            int[] newRow = compress(board[r]);
            newRow = merge(newRow);
            newRow = compress(newRow);

            // chck new row and
            board[r] = newRow;
        }

        addNewTile(boardSize);

        printBoard();
    }

    private int[] compress(int[] row){
        List<Integer> newRow = new ArrayList<>();
        for(int num : row){
            if(num!=0){
                newRow.add(num);
            }
        }

        while(newRow.size() < boardSize){
            newRow.add(0);
        }

        int[] result = new int[boardSize];

        int index = 0;
        for(Integer num : newRow){
            result[index] = num;
            index++;
        }

        return result;
    }

    //    i -> i+1 - > i+2

    private int[] merge(int[] row){

        for( int i=0; i < boardSize - 1; i++){
            if( row[i] == row[i+1]){
                row[i] *=2;
                row[i+1] = 0;
                i++;
            }
        }
        return row;
    }

    public void printBoard() {
        for(int[] row : board){
            for( int num : row){
                System.out.print(num + "  ");
            }
            System.out.println();
        }

        System.out.println("_____________________________");
        System.out.println("_____________________________");
    }

}



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        Game2048 game2048 = new Game2048(4);


        while(true){
            if(game2048.checkStatus() == "won"){
                System.out.println("game won");
                break;
            }
            else if(game2048.checkStatus() == "gameOver"){
                System.out.println("game over");
                break;
            }
            else{
                game2048.moveLeft();
            }
        }



    }
}