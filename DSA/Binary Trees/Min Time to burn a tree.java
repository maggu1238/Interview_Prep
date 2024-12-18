/*Given a binary tree and a target node. The task is to find the minimum time required to burn the complete binary tree if the target is set on fire. It is known that in 1 second all nodes connected to a given node get burned. Then all the nodes that are connected through one intermediate get burned in 2 seconds, and so on. That is its left child, right child, and parent.*/
//DFS
class Solution {
  public:
    int maxDepth(Node* n)
    // finding the most distant leaf node from given node
    {
        if (!n) return 0;
        return 1 + max( maxDepth(n->left) , maxDepth(n->right) );
    }

    int traverse(Node* n, int target, int &ret)
    {
        if (!n) return 0;
        // base case

        if (n->data == target) {
            ret = max( ret, maxDepth(n->right) );
            ret = max( ret, maxDepth(n->left) );
            return 1;
        }
        // target found, hence returning distance from it

        int val = traverse(n->left, target, ret);
        if (val)
            // (val != 0) means target was found at distance = val
        {
            ret = max( ret, val + maxDepth(n->right) );
            // finding max Depth on right as target was on left
            return val + 1;
        }

        val = traverse(n->right, target, ret);
        if (val)
            // (val != 0) means target was found at distance = val
        {
            ret = max( ret, val + maxDepth(n->left) );
            // finding max Depth on left as target was on right
            return val + 1;
        }

        return 0;
    }

    int minTime(Node* root, int target)
    {
        int ret = 0;
        traverse(root, target, ret);
        return ret;
    }
};

//BFS
int minTime(Node *root, int target) {
    
    // Base case
    if (root == nullptr) return -1;
    
    queue<Node*> q;
    q.push(root);
    Node* tar;
    
    // hash map to map the child nodes
    // to their parent nodes
    unordered_map<Node*, Node*> par;
    par[root] = nullptr;
    
    while (!q.empty()) {
        Node* curr = q.front();
        q.pop();
        
        // Set tar = curr if value
        // is equal.
        if (curr->data == target)
            tar = curr;
        
        // map the left child to its 
        // parent
        if (curr->left != nullptr) {
            par[curr->left] = curr;
            q.push(curr->left);
        }
        
        // map the right child to its
        // parent
        if (curr->right != nullptr) {
            par[curr->right] = curr;
            q.push(curr->right);
        }
    }
    
    // hash map to check if a node
    // has been visited or not.
    unordered_map<Node*, bool> vis;
    
    int ans = -1;
    
    q.push(tar);
    
    while (!q.empty()) {
        int size = q.size();
        while (size--) {
            Node* curr = q.front();
            vis[curr] = true;
            q.pop();
            
            // Push the left child node.
            if (curr->left != nullptr && !vis[curr->left])
                q.push(curr->left);
            
            // Push the right child node.
            if (curr->right != nullptr && !vis[curr->right])
                q.push(curr->right);
            
            // Push the parent node.    
            if (par[curr] != nullptr && !vis[par[curr]])
                q.push(par[curr]);
        }
        
        // increment the answer
        ans++;
    }
    
    return ans;
}