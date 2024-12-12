
/*/*You are given a tree (i.e. a connected, undirected graph that has no cycles) rooted at node 0 consisting of n nodes numbered from 0 to n - 1. The tree is represented by a 0-indexed array parent of size n, where parent[i] is the parent of node i. Since node 0 is the root, parent[0] == -1.

You are also given a string s of length n, where s[i] is the character assigned to node i.

Return the longest path in the tree such that no pair of adjacent nodes on the path have the same character assigned to them.*/
class Solution {
public:
    vector<int> longestPath(vector<int>& parent, string s) {
        int n = parent.size();
        vector<vector<int>> tree(n);

        // Build the adjacency list for the tree
        for (int i = 1; i < n; ++i) {
            tree[parent[i]].push_back(i);
        }

        vector<int> resultPath;
        int maxLength = 0;

        // DFS function to calculate the longest path
        pair<int, vector<int>> dfs(int node) {
            int longest = 0, secondLongest = 0;
            vector<int> longestPath, secondLongestPath;

            for (int child : tree[node]) {
                pair<int, vector<int>> childResult = dfs(child);
                int childPathLength = childResult.first;
                vector<int> childPath = childResult.second;

                // Only consider paths with different characters
                if (s[child] != s[node]) {
                    if (childPathLength > longest) {
                        secondLongest = longest;
                        secondLongestPath = longestPath;
                        longest = childPathLength;
                        longestPath = childPath;
                    } else if (childPathLength > secondLongest) {
                        secondLongest = childPathLength;
                        secondLongestPath = childPath;
                    }
                }
            }

            // Update the maximum path length
            if (longest + secondLongest + 1 > maxLength) {
                maxLength = longest + secondLongest + 1;
                resultPath = longestPath;
                resultPath.push_back(node);
                resultPath.insert(resultPath.end(), secondLongestPath.rbegin(), secondLongestPath.rend());
            }

            // Return the longest path starting from this node
            longestPath.push_back(node);
            return {longest + 1, longestPath};
        }

        dfs(0); // Start DFS from the root node

        reverse(resultPath.begin(), resultPath.end());
        return resultPath;
    }
};
