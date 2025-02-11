/*
public int kthLargest(TreeNode root, int k) {}


Follow up:
Given a list of kth values find return the number cooresponding


public int kthLargest(TreeNode root, List k) {}


Example:


             10
         /       \
      4          20
	/          /     \
  2           15     40
k = [2, 1, 30, 6]


output: [20, 40, null, 2]

*/



#include <bits/stdc++.h>
using namespace std;

struct TreeNode {
    int val;
    TreeNode *left, *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    unordered_map<int, int> resultMap; // Stores k-th largest results
    int count = 0; // Track visited nodes
    int index = 0; // Track processed k values

    void reverseInorder(TreeNode* root, vector<int>& kList, vector<int>& results) {
        if (!root || index >= kList.size()) return;

        // Visit right subtree (larger elements first)
        reverseInorder(root->right, kList, results);

        // Process current node
        count++;
        while (index < kList.size() && count == kList[index]) {
            resultMap[kList[index]] = root->val;
            index++;
        }

        // Visit left subtree (smaller elements)
        reverseInorder(root->left, kList, results);
    }

    vector<int> kthLargest(TreeNode* root, vector<int>& k) {
        vector<int> sortedK = k;
        sort(sortedK.begin(), sortedK.end()); // Sort k values in ascending order
        vector<int> results(k.size(), -1); // Default results initialized to -1 (null equivalent)

        reverseInorder(root, sortedK, results);

        // Map results back to original order
        for (int i = 0; i < k.size(); i++) {
            if (resultMap.find(k[i]) != resultMap.end()) {
                results[i] = resultMap[k[i]];
            } else {
                results[i] = -1; // k value out of range
            }
        }

        return results;
    }
};

// Driver Code
int main() {
    TreeNode* root = new TreeNode(10);
    root->left = new TreeNode(4);
    root->right = new TreeNode(20);
    root->left->left = new TreeNode(2);
    root->right->left = new TreeNode(15);
    root->right->right = new TreeNode(40);

    Solution sol;
    vector<int> k = {2, 1, 30, 6};
    vector<int> result = sol.kthLargest(root, k);

    cout << "[ ";
    for (int r : result) cout << (r == -1 ? "null" : to_string(r)) << " ";
    cout << "]" << endl;

    return 0;
}
