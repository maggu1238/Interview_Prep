/*We are given the root of a binary tree with n nodes and an array, queries, of size m. Each query represents the root of a subtree that should be removed from the tree. The task here is to determine the height of the binary tree after each query, i.e., once a subtree is removed. We'll store the updated heights against each query in an array and return it.*/

/*A few points to be considered:
All the values in the tree are unique.
It is guaranteed that queries[i] will not be equal to the value of the root.
The queries are independent, so the tree returns to its initial state after each query.*/

std::unordered_map<int, int> nodeDepth{};
std::unordered_map<int, int> nodeHeight{};

int tree_dfs(TreeNode<int>* node, int depth ) {
	if (!node) {
		return -1;
	}
	nodeDepth[node->data] = depth;
	int height = std::max(tree_dfs(node->left, depth + 1), tree_dfs(node->right, depth + 1)) + 1;
	nodeHeight[node->data] = height;
	return height;
}

std::vector<int> HeightsAfterQueries(TreeNode<int>* root, std::vector<int>& queries) {
	tree_dfs(root, 0);

	std::unordered_map<int, std::vector<int>> depthGroups{};

	for (auto& entry : nodeDepth) {
		int value = entry.first;
		int depth = entry.second;
		depthGroups[depth].push_back(nodeHeight[value]);
	}
	
	for (auto& entry : depthGroups) {
		std::sort(entry.second.rbegin(), entry.second.rend() );
	}
	
	std::vector<int> result;
	for (int q : queries) {
		int depth = nodeDepth[q];
		const auto& heights = depthGroups[depth];

		if (heights.size() == 1) {
			result.push_back(depth - 1);
		} else {
			if (heights[0] == nodeHeight[q]) {
				result.push_back(heights[1] + depth);
			} else {
				result.push_back(heights[0] + depth);
			}
		}
	}

	return result;
}