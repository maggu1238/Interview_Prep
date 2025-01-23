import java.util.HashMap;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

//    Snakes: 98->9, 78->49, 33->2
//
//    Ladders: 11->32, 15->75, 52->99
    
    private static int rollDice(){
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    private static void playGame(HashMap<Integer, Integer> snakes, HashMap<Integer, Integer> ladders){
        int[] playerPositions = {1,1};
        int currPlayer = 0;

        while(true){
            System.out.println("Player->" + currPlayer + " turn");
            int rollsInTurn = 0;
            boolean extraTurn = true;

            while(extraTurn){
                extraTurn = false;
                int roll = rollDice();
                rollsInTurn++;
                System.out.println("Player " + currPlayer + " rolled a " + roll);
                System.out.println("Player->" + currPlayer + " is at position-> " + playerPositions[currPlayer]);
                playerPositions[currPlayer] += roll;


                if(playerPositions[currPlayer] > 100){
                    playerPositions[currPlayer] -= roll;
                }
                else{
                    int position = playerPositions[currPlayer];
                    if(snakes.containsKey(position)){
                        System.out.println("Player " + currPlayer + " bitten by snake at position " + position);
                        playerPositions[currPlayer] = snakes.get(position);
                    } else if (ladders.containsKey(position)) {
                        System.out.println("Player " + currPlayer + " got a ladder at position " + position);
                        playerPositions[currPlayer] = ladders.get(position);
                    }
                }

                System.out.println("Player->" + currPlayer + " is at position-> " + playerPositions[currPlayer] + " after the roll");

                if(playerPositions[currPlayer] == 100){
                    System.out.println("Player " + currPlayer + " wins the game.");
                    return;
                }

                if(roll == 6 && rollsInTurn < 3){
                    System.out.println("Player-> " + currPlayer + " gets other chance to roll");
                    extraTurn = true;
                }
            }

            currPlayer = (currPlayer + 1)%2;
        }
    }

    public static void main(String[] args) {

        HashMap<Integer, Integer> snakes  = new HashMap<>();
        HashMap<Integer, Integer> ladders  = new HashMap<>();

        snakes.put(98, 9);
        snakes.put(78,49);
        snakes.put(33,2);
        ladders.put(11,32);
        ladders.put(15,75);
        ladders.put(52,99);

        playGame(snakes, ladders);

    }
}