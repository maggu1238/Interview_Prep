/*Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after removing k digits from num.*/

class Solution {
    public String removeKdigits(String nums, int k) {
        Stack<Integer> s = new Stack<>();
        int i =0;

        while(i < nums.length()){
            while(!s.isEmpty() && s.peek() > nums.charAt(i) - '0' && k > 0){
                s.pop();
                k--;
            }
            s.push(nums.charAt(i) - '0');
            i++;
        }

        while(k > 0){
            s.pop();
            k--;
        }
        
        StringBuilder result = new StringBuilder();
        while(!s.isEmpty()){
            result.append(s.pop());
        }
        result  = result.reverse();

        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        
        if(result.length() == 0)
            return "0";
        return result.toString();
    }
}