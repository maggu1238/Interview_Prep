/*
You are given a binary matrix grid[m][n] where:

grid[i][j] = 1 means boy i can invite girl j.
Each boy can send at most one invitation.
Each girl can accept at most one invitation.
Your goal is to maximize the number of accepted invitations.
*/


/*
Hungarian Algorithm & Maximum Bipartite Matching
The Hungarian Algorithm (also known as the Kuhn-Munkres Algorithm) is an algorithm used to find the maximum matching in a bipartite graph. It is used to solve problems where two sets of elements need to be matched optimally, such as:

Assigning jobs to workers based on skill compatibility.
Matching boys and girls in a dating problem.
Assigning tasks to processors in an optimal way.



A bipartite graph is a graph where nodes can be divided into two independent sets (e.g., boys and girls). Edges only exist between nodes of different sets.

A matching is a set of edges where:

Each node in set A is connected to at most one node in set B.
Each node in set B is connected to at most one node in set A.
A maximum matching means we match the highest possible number of elements.
*/

/*

Step 2: Hungarian Algorithm (DFS + Augmenting Path)
The Hungarian Algorithm is an approach to maximize bipartite matching using augmenting paths.

How It Works?
Start with an empty matching.
Try to match each boy to a girl.
If a girl is already matched, try to find another match for her current partner using DFS.
If we find a way to reassign her partner, the girl is freed up, and the new boy-girl match is made.
Repeat this process for all boys.
*/

/*
Step 3: Understanding the Problem Statement
Problem Description
You are given an m × n binary grid where:
grid[i][j] = 1 means boy i can send an invitation to girl j.
Each boy can send at most one invitation.
Each girl can accept at most one invitation.
Your task is to find the maximum number of accepted invitations.
*/


#include <bits/stdc++.h>
using namespace std;


// TC:  O(m *n(for dfs for one boy in worst case))


class Solution {
public:
    // DFS function to find an augmenting path
    bool bpm(vector<vector<int>>& grid, int u, vector<int>& girlMatch, vector<bool>& visited) {
        for (int v = 0; v < grid[0].size(); v++) {
            if (grid[u][v] && !visited[v]) {
                visited[v] = true; // Mark girl as visited

                // If girl v is free or can be matched to another boy
                if (girlMatch[v] == -1 || bpm(grid, girlMatch[v], girlMatch, visited)) {
                    girlMatch[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    int maxInvitations(vector<vector<int>>& grid) {
        int m = grid.size(), n = grid[0].size();
        vector<int> girlMatch(n, -1); // Track girl matched with which boy
        int result = 0;

        for (int boy = 0; boy < m; boy++) {
            vector<bool> visited(n, false);
            if (bpm(grid, boy, girlMatch, visited)) {
                result++;
            }
        }
        return result;
    }
};

// Driver code
int main() {
    Solution sol;
    vector<vector<int>> grid = {
        {1, 1, 0},
        {1, 0, 1},
        {0, 0, 1}
    };
    cout << sol.maxInvitations(grid) << endl;  // Output: 2
    return 0;
}
