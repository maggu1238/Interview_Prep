/**Given a tree where each node represents a person, and each person has a "fun" value, we want to find the maximum total "fun" value such that no two directly connected people are both included in the set (i.e., the set is an independent set). */

/**State Definition:

Let dp[u][0] represent the maximum fun that can be obtained from the subtree rooted at node u when node u is excluded from the independent set.
Let dp[u][1] represent the maximum fun that can be obtained from the subtree rooted at node u when node u is included in the independent set.
Recurrence Relations:

If node u is excluded from the independent set, then its children can either be included or excluded. So:
dp[u][0]= v∈children(u) ∑ max(dp[v][0],dp[v][1])

If node u is included, its children must be excluded. So:
𝑑𝑝[𝑢][1]= fun[𝑢] + ∑𝑣∈children(𝑢)𝑑𝑝[𝑣][0] 

Where fun[u] is the fun value of node u.

Base Case:

For a leaf node (a node with no children), the value of dp[u][0] = 0 (exclude it) and dp[u][1] = fun[u] (include it).
Final Solution:

The solution to the problem is the maximum of dp[root][0] and dp[root][1], where root is the root of the tree.
 */


import java.util.*;

class TreeNode {
    int funValue;
    List<TreeNode> children;

    public TreeNode(int funValue) {
        this.funValue = funValue;
        this.children = new ArrayList<>();
    }
}

public class MaximizeFunInParty {

    // Method to maximize fun in a party (Independent Set in a Tree)
    public static int maximizeFunInParty(TreeNode root) {
        Map<TreeNode, int[]> dp = new HashMap<>();

        // Helper function to perform DFS and calculate the dp values
        dfs(root, dp);

        // Final result is the max fun we can get from the root, either excluding or including the root
        return Math.max(dp.get(root)[0], dp.get(root)[1]);
    }

    // DFS to compute dp values
    private static int[] dfs(TreeNode node, Map<TreeNode, int[]> dp) {
        // Base case: if the node is null, return (0, 0) for exclude and include
        if (node == null) {
            return new int[]{0, 0};
        }

        // If dp for this node is already computed, return it
        if (dp.containsKey(node)) {
            return dp.get(node);
        }

        // Initialize fun values for exclude (0) and include (node.funValue)
        int excludeFun = 0;
        int includeFun = node.funValue;

        // Recur for all children
        for (TreeNode child : node.children) {
            int[] childDp = dfs(child, dp);
            excludeFun += Math.max(childDp[0], childDp[1]); // Max of excluding or including the child
            includeFun += childDp[0]; // If we include the node, child must be excluded
        }

        // Store the dp values for the current node
        dp.put(node, new int[]{excludeFun, includeFun});
        return dp.get(node);
    }

    // Example usage:
    public static void main(String[] args) {
        // Construct the tree
        TreeNode root = new TreeNode(10);  // root node with fun value 10
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(20);
        TreeNode node3 = new TreeNode(30);
        TreeNode node4 = new TreeNode(10);

        root.children.add(node1);
        root.children.add(node2);
        node1.children.add(node3);
        node2.children.add(node4);

        // Calculate the maximum fun
        int maxFun = maximizeFunInParty(root);
        System.out.println("Maximum Fun: " + maxFun);
    }
}
