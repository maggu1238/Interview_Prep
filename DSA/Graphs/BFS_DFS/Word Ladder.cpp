/*A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.*/
class Solution {
public:
    int ladderLength(string beginWord, string endWord, vector<string>& wordList) {
        unordered_map<string, vector<string>> mp;
        unordered_set<string> s;

        unordered_map<string, bool> visited;
        visited[beginWord] = false;
        //visited[endWord] = false;
        
        wordList.push_back(beginWord);
        //wordList.push_back(endWord);

        for( int i = 0; i < wordList.size(); i++){
            s.insert(wordList[i]);
            visited[wordList[i]] = false;
        }

        for(int i = 0; i < wordList.size(); i++){
            string word =  wordList[i];
            for( int j = 0; j  < word.length(); j++){
                for( int k = 0;  k  < 26; k++){
                    char ch = 'a' + k;
                    string temp = word;
                    temp[j] = ch;
                    if(s.find(temp) != s.end() && temp != word){
                        mp[word].push_back(temp);
                    }
                }
            }
        }

        queue<string> q;
        q.push(beginWord);
        int size = 1;
        int length = 1;
        visited[beginWord] = true;

        while(!q.empty()){
            string tempWord = q.front();
            q.pop();
            size--;
            for(int i =0; i < mp[tempWord].size(); i++){
                string child = mp[tempWord][i];
                if( child == endWord){
                    return length+1;
                }
                else if(visited[child] == false){
                    q.push(child);
                    visited[child] = true;
                }
            }
            if(size == 0){
                length++;
                size = q.size();
            }
        }

        return 0;
    }
};