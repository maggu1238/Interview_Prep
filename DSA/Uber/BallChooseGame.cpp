/*
Players a and b are playing the famous ball score game. there are some balls placed on a table with each with a value on it. game start with a coin toss.
 heads means a starts, else b starts. winner takes first turn. during each turn a player is allowed to pick at most k balls from table. score is sum of values of all balls taken by player.
  b will only pick ball whose sum of digits of ball is maximum, if theres a tie he chooses either one. a doesn't care about that he'll pick any ball. both want to maximize their score so
   both will play optimally. print the score both a and b will achieve.
*/

#include <bits/stdc++.h>
using namespace std;

// Function to compute the sum of digits of a number
int sumOfDigits(int num) {
    int sum = 0;
    while (num > 0) {
        sum += num % 10;
        num /= 10;
    }
    return sum;
}

// Function to play the game optimally
void ballGame(vector<int>& balls, int k, char firstPlayer) {
    int aScore = 0, bScore = 0;
    bool aTurn = (firstPlayer == 'A'); // True if A starts

    // Sort balls in descending order (so A can pick greedily)
    sort(balls.rbegin(), balls.rend());

    // Max heap (priority queue) for B: sorted by (sum of digits, value)
    priority_queue<pair<int, int>> bHeap;
    for (int ball : balls) {
        bHeap.push({sumOfDigits(ball), ball});
    }

    // Game simulation
    int index = 0; // Pointer for A's selections in sorted array
    while (!balls.empty()) {
        vector<int> toRemove;
        int take = min(k, (int)balls.size());

        if (aTurn) {
            // A picks highest k remaining balls
            for (int i = 0; i < take; i++) {
                aScore += balls[index];
                toRemove.push_back(balls[index]);
                index++;
            }
        } else {
            // B picks k balls with highest sum of digits
            for (int i = 0; i < take; i++) {
                while (!bHeap.empty() && find(balls.begin(), balls.end(), bHeap.top().second) == balls.end()) {
                    bHeap.pop(); // Remove already taken balls
                }
                if (!bHeap.empty()) {
                    bScore += bHeap.top().second;
                    toRemove.push_back(bHeap.top().second);
                    bHeap.pop();
                }
            }
        }

        // Remove selected balls from table
        for (int x : toRemove)
            balls.erase(find(balls.begin(), balls.end(), x));

        // Switch turn
        aTurn = !aTurn;
    }

    cout << aScore << " " << bScore << endl;
}

int main() {
    vector<int> balls = {35, 23, 89, 12, 45, 67, 56, 78};
    int k = 2;
    char firstPlayer = 'A'; // Change to 'B' if B wins the coin toss

    ballGame(balls, k, firstPlayer);
    return 0;
}
