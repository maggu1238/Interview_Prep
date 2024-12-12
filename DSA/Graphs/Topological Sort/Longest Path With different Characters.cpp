/*You are given a tree (i.e. a connected, undirected graph that has no cycles) rooted at node 0 consisting of n nodes numbered from 0 to n - 1. The tree is represented by a 0-indexed array parent of size n, where parent[i] is the parent of node i. Since node 0 is the root, parent[0] == -1.

You are also given a string s of length n, where s[i] is the character assigned to node i.

Return the length of the longest path in the tree such that no pair of adjacent nodes on the path have the same character assigned to them.*/


//dfs Solution
class Solution {
public:
    int longestPath(vector<int>& parent, string s) {
        int n = parent.size();
        vector<vector<int>> tree(n);

        // Build the adjacency list for the tree
        for (int i = 1; i < n; ++i) {
            tree[parent[i]].push_back(i);
        }

        int maxLength = 0;

        // DFS function to calculate the longest path
        int dfs(int node) {
            int longest = 0, secondLongest = 0;

            for (int child : tree[node]) {
                int childPathLength = dfs(child);

                // Only consider paths with different characters
                if (s[child] != s[node]) {
                    if (childPathLength > longest) {
                        secondLongest = longest;
                        longest = childPathLength;
                    } else if (childPathLength > secondLongest) {
                        secondLongest = childPathLength;
                    }
                }
            }

            // Update the maximum path length
            maxLength = max(maxLength, longest + secondLongest + 1);

            // Return the longest path starting from this node
            return longest + 1;
        }

        dfs(0); // Start DFS from the root node

        return maxLength;
    }
};

// Topo Solution 
int longestPath(std::vector<int>& parent, std::string s) {
    int n = parent.size();
    std::vector<int> inDegree(n, 0);
    std::queue<int> q;

    for (int node = 1; node < n; ++node) {
        inDegree[parent[node]]++;
    }

    std::vector<std::vector<int>> longestChains(n, std::vector<int>(2, 0));
    int longestPathLength = 1;
    for (int node = 0; node < n; ++node) {
        if (inDegree[node] == 0) {
            longestChains[node][0] = 1;
            q.push(node);
        }
    }

    while (!q.empty()) {
        int currentNode = q.front(); q.pop();
        int par = parent[currentNode];

        if (par != -1) {
            int longestChainFromCurrent = longestChains[currentNode][0];

            if (s[currentNode] != s[par]) {
                if (longestChainFromCurrent > longestChains[par][0]) {
                    longestChains[par][1] = longestChains[par][0];
                    longestChains[par][0] = longestChainFromCurrent;
                } else if (longestChainFromCurrent > longestChains[par][1]) {
                    longestChains[par][1] = longestChainFromCurrent;
                }
            }

            longestPathLength = std::max(longestPathLength, longestChains[par][0] + longestChains[par][1] + 1);

            inDegree[par]--;
            if (inDegree[par] == 0) {
                longestChains[par][0]++;
                q.push(par);
            }
        }
    }

    return longestPathLength;
}