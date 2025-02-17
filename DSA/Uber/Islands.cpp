/* 
The problem itself was about designing a data structure to represent a terrain of islands in a sea of water, where each island is denoted by a coordinate (x, y). The data structure needed to support two methods:


add(x, y) – Add a piece of land.
is_island(x, y) – Query whether a coordinate is land or water.
*/

#include <iostream>
#include <vector>
using namespace std;

class DSU {
public:
    vector<int> parent, rank;
    int count;  // Number of connected components (islands)

    DSU(int n) {
        parent.resize(n, -1);
        rank.resize(n, 0);
        count = 0;
    }

    int find(int x) {
        if (parent[x] == -1) return -1;  // Not yet part of an island
        if (parent[x] != x) parent[x] = find(parent[x]); // Path Compression
        return parent[x];
    }

    void unite(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == -1 || rootY == -1 || rootX == rootY) return;  // Ignore invalid/unnecessary unions
        
        // Union by Rank
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        count--; // Since two components merged, reduce island count
    }

    void addLand(int x) {
        if (parent[x] != -1) return;  // Already land
        parent[x] = x;
        count++;
    }

    int getIslandCount() {
        return count;
    }
};

class IslandCounter {
private:
    int m, n;
    DSU dsu;
    vector<int> dirs = {-1, 0, 1, 0, -1}; // 4-directional movement

public:
    IslandCounter(int rows, int cols) : m(rows), n(cols), dsu(rows * cols) {}

    void addIsland(int r, int c) {
        int idx = r * n + c;
        dsu.addLand(idx);

        // Try to merge with 4 neighboring lands
        for (int d = 0; d < 4; d++) {
            int nr = r + dirs[d], nc = c + dirs[d + 1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                int neighborIdx = nr * n + nc;
                dsu.unite(idx, neighborIdx);
            }
        }
    }

    int getNumberOfIslands() {
        return dsu.getIslandCount();
    }
};

int main() {
    IslandCounter counter(3, 3);

    counter.addIsland(0, 0);
    cout << "Islands: " << counter.getNumberOfIslands() << endl;  // Output: 1

    counter.addIsland(0, 1);
    cout << "Islands: " << counter.getNumberOfIslands() << endl;  // Output: 1 (merged)

    counter.addIsland(1, 2);
    cout << "Islands: " << counter.getNumberOfIslands() << endl;  // Output: 2

    counter.addIsland(2, 1);
    cout << "Islands: " << counter.getNumberOfIslands() << endl;  // Output: 3

    counter.addIsland(1, 1);
    cout << "Islands: " << counter.getNumberOfIslands() << endl;  // Output: 1 (all merged)

    return 0;
}
