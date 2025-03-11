/*
Given a string s, return whether s is a valid number.

For example, all the following are valid numbers: "2", "0089", "-0.1", "+3.14", "4.",
 "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789", 
 while the following are not valid numbers: "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53".

Formally, a valid number is defined using one of the following definitions:

An integer number followed by an optional exponent.
A decimal number followed by an optional exponent.
An integer number is defined with an optional sign '-' or '+' followed by digits.

A decimal number is defined with an optional sign '-' or '+' followed by one of the following definitions:

Digits followed by a dot '.'.
Digits followed by a dot '.' followed by digits.
A dot '.' followed by digits.
An exponent is defined with an exponent notation 'e' or 'E' followed by an integer number.

The digits are defined as one or more digits.
*/

#include <iostream>
using namespace std;

class Solution {
public:
    bool isNumber(string s) {
        int i = 0, n = s.size();
        
        // 1. Trim leading spaces
        while (i < n && s[i] == ' ') i++;
        
        // 2. Handle optional +/- sign
        if (i < n && (s[i] == '+' || s[i] == '-')) i++;
        
        bool isNumeric = false, isDecimal = false, isExponential = false;
        
        while (i < n) {
            char c = s[i];

            if (isdigit(c)) {
                isNumeric = true;  // At least one digit found
            } else if (c == '.') {
                if (isDecimal || isExponential) return false; // '.' cannot appear after 'e'
                isDecimal = true;
            } else if (c == 'e' || c == 'E') {
                if (isExponential || !isNumeric) return false; // 'e' cannot appear twice or without preceding digits
                isExponential = true;
                isNumeric = false; // Need at least one digit after 'e'
            } else if (c == '+' || c == '-') {
                if (s[i - 1] != 'e' && s[i - 1] != 'E') return false; // Sign can only follow 'e'
            } else if (c == ' ') {
                break; // Stop at trailing spaces
            } else {
                return false; // Invalid character
            }
            i++;
        }
        
        // 3. Skip trailing spaces
        while (i < n && s[i] == ' ') i++;
        
        return isNumeric && i == n;
    }
};

int main() {
    Solution sol;
    cout << sol.isNumber("3.14") << endl;      // true
    cout << sol.isNumber("2e10") << endl;      // true
    cout << sol.isNumber(" -90E3  ") << endl;  // true
    cout << sol.isNumber("1e") << endl;        // false
    cout << sol.isNumber("e3") << endl;        // false
    cout << sol.isNumber("abc") << endl;       // false
    cout << sol.isNumber("99e2.5") << endl;    // false
    cout << sol.isNumber(" 6e-1") << endl;     // true
    cout << sol.isNumber("53.5e93") << endl;   // true
    cout << sol.isNumber(" --6") << endl;      // false
    return 0;
}
