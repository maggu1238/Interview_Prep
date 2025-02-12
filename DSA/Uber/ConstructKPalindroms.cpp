/*
Given a string s and an integer k, return true if you can use all the characters in s to construct non-empty k 
palindrome strings
 or false otherwise.
*/


class Solution {
    public:
        bool canConstruct(string s, int k) {
            if (s.length() < k) return false;
    
            sort(s.begin(), s.end());
            int oddCount = 0;
    
            for (int i = 0; i < s.length(); ) {
                char current = s[i];
                int count = 0;
                while (i < s.length() && s[i] == current) {
                    count++;
                    i++;
                }
                if (count % 2 != 0) oddCount++;
            }
    
            return oddCount <= k;
        }
    };