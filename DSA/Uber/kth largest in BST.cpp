/*
Given a binary search tree (BST) find the kth largest element.
*/

#include <queue>

class Solution {
public:
    void inorder(TreeNode* root, priority_queue<int, vector<int>, greater<int>>& pq, int k) {
        if (!root) return;

        inorder(root->left, pq, k);

        pq.push(root->val);
        if (pq.size() > k) pq.pop();

        inorder(root->right, pq, k);
    }

    int kthLargest(TreeNode* root, int k) {
        priority_queue<int, vector<int>, greater<int>> pq; // Min heap
        inorder(root, pq, k);
        return pq.top();
    }
};

