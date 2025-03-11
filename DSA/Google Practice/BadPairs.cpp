/*
ou’re given a string which may contain "bad pairs".
A bad pair is defined as a pair of adjacent characters, where they are the same letter in different cases. For example, "xX" is a bad pair, but "xx" or "XX" are not.
Implement a solution to remove the bad pairs from the string.
Sample:
	• Input: abxXw
	• Output: abw
Additional Case:
If the input string is empty:
	• Input: ""
	• Output: ""
*/

#include <iostream>
#include <stack>
#include <string>

std::string removeBadPairs(const std::string& s) {
    std::stack<char> st;

    for (char c : s) {
        if (!st.empty() && abs(st.top() - c) == 32) {
            // Found a bad pair (e.g., 'x' and 'X'), so remove the top character
            st.pop();
        } else {
            // Push the current character to stack
            st.push(c);
        }
    }

    // Build the result string from the stack
    std::string result;
    while (!st.empty()) {
        result += st.top();
        st.pop();
    }
    
    // Reverse the string since the stack stores it in reverse order
    std::reverse(result.begin(), result.end());
    
    return result;
}

// 2 pointers approach
class Solution {
    public:
        string makeGood(string s) {
            string result;
        
            for (char c : s) {
                if (!result.empty() && tolower(result.back()) == tolower(c) && result.back() != c) {
                    //invalid
                    result.pop_back();
                } else {
                    //valid
                    result.push_back(c);
                }
            }
            
            return result;
        }
    };

int main() {
    std::string s = "aAbBcCxyX";
    std::cout << "Processed String: " << removeBadPairs(s) << std::endl;
    return 0;
}


