/*We are given an array asteroids of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.*/

class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        int i  = 0;
        Stack<Integer> s = new Stack<>();

        while(i < asteroids.length){
            if(s.isEmpty()){
                s.push(asteroids[i]);
            }
            else{
                if(asteroids[i] < 0){
                    while(!s.isEmpty() && s.peek() > 0 && s.peek() < -asteroids[i]){
                        s.pop();
                    }

                    if(s.isEmpty()){
                        s.push(asteroids[i]);
                    }
                    else{
                        if(s.peek() < 0 ){
                            s.push(asteroids[i]);
                        }
                        else if(s.peek() == -asteroids[i])
                        {
                            s.pop();
                        }
                    }
                }
                else{
                    s.push(asteroids[i]);
                }
            }
            i++;
        }

        int size =s.size();

        int[] res = new int[s.size()];

        while(!s.isEmpty()){
            res[size - 1] = s.peek();
            s.pop();
            size--;
        }
        return res;
    }
}