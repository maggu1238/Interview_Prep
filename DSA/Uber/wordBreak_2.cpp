/**
 * Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.

Note that the same word in the dictionary may be reused multiple times in the segmentation.
 */

class Solution {
    public:
    
        void dfs(string s, int startIndex, vector<string>& res, vector<string>& temp,  vector<string>& wordDict){
            if(startIndex >= s.length()){
                string resultString = temp[0];
                for( int i = 1; i < temp.size(); i++){
                    resultString += " " + temp[i];
                }
                res.push_back(resultString);
                return;
            }
    
            for(int i =0; i < wordDict.size(); i++){
                string word = wordDict[i];
                string sampleWord = s.substr(startIndex, word.length());
                if(word == sampleWord){
                    temp.push_back(word);
                    dfs(s, startIndex + word.length(), res, temp, wordDict );
                    temp.pop_back();
                }
            }
    
            return;
        }
        vector<string> wordBreak(string s, vector<string>& wordDict) {
    
            vector<string> res;
            vector<string> temp;
    
            dfs(s, 0,  res, temp, wordDict);
    
            return res;
        }
    };