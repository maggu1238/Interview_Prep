/*

Find maximum length of a substring of a string with first charachter lexicographically smaller than its last charachter.
assume string length 10^5 char long, assume 26 small case english letters in string
*/

#include <iostream>
#include <string>
#include <algorithm>
using namespace std;

int maxLengthSubstring(string &s) {
    int n = s.size();
    int left = 0;
    int maxLength = 0;

    for (int right = 0; right < n; ++right) {
        // Expand the window by moving the `right` pointer
        while (left < right && s[left] >= s[right]) {
            left++;  // Shrink the window from the left side if condition is violated
        }

        // If the first character is lexicographically smaller than the last, check length
        if (s[left] < s[right]) {
            maxLength = max(maxLength, right - left + 1);
        }
    }
    
    return maxLength;
}

int main() {
    string s = "abcbaabbbccccd";  // Example input
    cout << "Max Length: " << maxLengthSubstring(s) << endl;
    return 0;
}
