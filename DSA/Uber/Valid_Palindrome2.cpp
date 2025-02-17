/*Given a string s, return true if the s can be palindrome after deleting at most one character from it.*/

#include <iostream>
#include <cctype>
using namespace std;

// Helper function to check if a substring is a palindrome
bool isPalindrome(const string& s, int left, int right) {
    while (left < right) {
        if (s[left] != s[right])
            return false;
        left++;
        right--;
    }
    return true;
}

// Function to check if we can form a palindrome by removing at most one character
bool validPalindrome(string s) {
    string filtered;
    
    // Step 1: Preprocess the string (remove non-alphanumeric & convert to lowercase)
    for (char c : s) {
        if (isalnum(c))  // Keep only letters and numbers
            filtered += tolower(c);
    }

    // Step 2: Use two-pointer approach
    int left = 0, right = filtered.size() - 1;
    
    while (left < right) {
        if (filtered[left] != filtered[right]) {
            // Try removing left character or right character
            return isPalindrome(filtered, left + 1, right) || isPalindrome(filtered, left, right - 1);
        }
        left++;
        right--;
    }
    
    return true;
}

// Driver code
int main() {
    string s = "A man, a plan, a canal: Panama"; // Example input
    cout << (validPalindrome(s) ? "True" : "False") << endl;
    return 0;
}
