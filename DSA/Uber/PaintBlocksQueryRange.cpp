/*
n sticks, each initially colored.
m painters, each performing an operation to paint a specified range of sticks with a new color.
Each operation is defined by three integers:


l: The starting index of the range to paint.
r: The ending index of the range to paint.
color: The color with which to paint the sticks in the range [l, r].
The goal is to determine the final color of all the sticks after all operations have been applied.
*/

#include <iostream>
#include <vector>
using namespace std;

class SegmentTree {
    vector<int> tree, lazy;
    int n;

public:
    SegmentTree(int size) {
        n = size;
        tree.assign(4 * n, 0);  // Initially all sticks have color 0
        lazy.assign(4 * n, 0);
    }

    void propagate(int node, int start, int end) {
        if (lazy[node] != 0) {  // Apply pending color update
            tree[node] = lazy[node];  // Update segment with latest color
            if (start != end) {  // Push update to children
                lazy[2 * node] = lazy[node];
                lazy[2 * node + 1] = lazy[node];
            }
            lazy[node] = 0;  // Clear lazy value
        }
    }

    void update(int node, int start, int end, int L, int R, int color) {
        propagate(node, start, end);

        if (start > R || end < L) return;  // No overlap
        if (L <= start && end <= R) {  // Full overlap
            lazy[node] = color;
            propagate(node, start, end);
            return;
        }

        // Partial overlap
        int mid = (start + end) / 2;
        update(2 * node, start, mid, L, R, color);
        update(2 * node + 1, mid + 1, end, L, R, color);
    }

    int query(int node, int start, int end, int idx) {
        propagate(node, start, end);
        if (start == end) return tree[node];  // Leaf node
        int mid = (start + end) / 2;
        if (idx <= mid) return query(2 * node, start, mid, idx);
        else return query(2 * node + 1, mid + 1, end, idx);
    }

    void updateRange(int L, int R, int color) {
        update(1, 0, n - 1, L, R, color);
    }

    vector<int> getFinalColors() {
        vector<int> result(n);
        for (int i = 0; i < n; i++) {
            result[i] = query(1, 0, n - 1, i);
        }
        return result;
    }
};

int main() {
    int n = 10;  // Number of sticks
    SegmentTree segTree(n);

    vector<vector<int>> operations = {
        {2, 5, 3},  // Paint [2,5] with color 3
        {4, 7, 5},  // Paint [4,7] with color 5
        {1, 3, 2}   // Paint [1,3] with color 2
    };

    for (auto &op : operations) {
        segTree.updateRange(op[0] - 1, op[1] - 1, op[2]);
    }

    vector<int> result = segTree.getFinalColors();
    cout << "Final Colors: ";
    for (int c : result) cout << c << " ";
    cout << endl;

    return 0;
}
