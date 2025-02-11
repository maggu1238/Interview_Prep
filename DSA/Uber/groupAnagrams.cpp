/*
Given an array of strings strs, group the 
anagrams
 together. You can return the answer in any order.
*/

#include <bits/stdc++.h>
using namespace std;

vector<vector<string>> groupAnagrams(vector<string>& strs) {
    unordered_map<string, vector<string>> anagramGroups;

    for (string& word : strs) {
        string sortedWord = word;
        sort(sortedWord.begin(), sortedWord.end());  // Sort characters
        anagramGroups[sortedWord].push_back(word);   // Group by sorted key
    }

    vector<vector<string>> result;
    for (auto& group : anagramGroups) {
        result.push_back(group.second);
    }

    return result;
}

int main() {
    vector<string> words = {"eat", "tea", "tan", "ate", "nat", "bat"};
    vector<vector<string>> groupedAnagrams = groupAnagrams(words);

    for (auto& group : groupedAnagrams) {
        for (string& word : group) cout << word << " ";
        cout << "\n";
    }
    
    return 0;
}
