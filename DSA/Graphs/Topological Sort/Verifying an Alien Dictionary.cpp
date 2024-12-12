/*You’re given a list of words with lowercase English letters in a different order, written in an alien language. The order of the alphabet is some permutation of lowercase letters of the English language.

We have to return TRUE if the given list of words is sorted lexicographically in this alien language.*/

bool VerifyAlienDictionary(vector<string>& words, string order){

    // Replace this placeholder return statement with your code
    unordered_map<char, int> charMap;
    for ( int i =0; i < order.length(); i++){
        charMap[order[i]] = i;
    }
    for ( int i = 0; i< words.size() - 1; i++){
        string word1 = words[i];
        string word2 = words[i+1];

        int index1, index2;
        index1 = 0;
        index2 = 0;
        while(index1 < word1.length() && index2 < word2.length()){
            char ch1 = word1[index1];
            char ch2 = word2[index2];

            if (ch1 == ch2){
                index1++;
                index2++;
            }
            else{
                if ( charMap[ch1] > charMap[ch2])
                {
                    return false;
                }
                else{
                    break;
                }
            }
        }

        if ( index2 == word2.length() && index1 < word1.size()){
            return false;
        }

    }


    return true;
}