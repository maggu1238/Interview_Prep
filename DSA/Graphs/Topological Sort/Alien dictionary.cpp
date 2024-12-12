/*In this challenge, you are given a list of words written in an alien language, where the words are sorted lexicographically by the rules of this language. Surprisingly, the aliens also use English lowercase letters, but possibly in a different order.

Given a list of words written in the alien language, you have to return a string of unique letters sorted in the lexicographical order of the alien language as derived from the list of words.

If there’s no solution, that is, no valid lexicographical ordering, you can return an empty string.*/

std::string FindDictionary(std::vector<std::string>& words) {
    std::unordered_map<char, std::vector<char>> adjList;
    std::unordered_map<char, int> counts;
    for(auto message : words) {
        for(auto c : message) {
            counts[c] = 0;
            adjList[c] = {};
        }
    }

    for (int i = 0; i < words.size() - 1; i++) {
        std::string message1 = words[i];
        std::string message2 = words[i + 1];

        // To check if the second word is prefix for the first or not
        if (message1.size() > message2.size() && message1.find(message2) == 0) {
            return "";
        }
        
        for (int j = 0; j < std::min(message1.size(), message2.size()); j++) {
            if (message1[j] != message2[j]) {
                adjList[message1[j]].push_back(message2[j]);
                counts[message2[j]] = counts[message2[j]] + 1;
                break;
            }
        }
    }
    
    std::string result = "";
    std::queue<char> sourcesQueue;
    for (auto itr : counts) {
        char c = itr.first;
        if (itr.second == 0) {
            sourcesQueue.push(c);
        }
    }

    while (!sourcesQueue.empty()) {
        char c = sourcesQueue.front();
        sourcesQueue.pop();
        result += c;
        
        for (char next : adjList[c]) {
            counts[next] = counts[next] - 1;
            if (counts[next] == 0) {
                sourcesQueue.push(next);
            }
        }
    }
    
    // To check cycle
    if (result.size() < counts.size()) {
        return "";
    }
    return result;
}