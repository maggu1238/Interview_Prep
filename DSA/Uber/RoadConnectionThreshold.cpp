/*
1-> n cities are given and a threshold is given and there is a bi-directional road between x and y if
x%z==0
y%z==0
z>threshold
Make connections and later they will provide different source and target cities, 
tell whether a road is there or not(road can be indirect as well).
*/

class DSU {
    vector<int> parent;
    vector<int> rank;

    DSU(int n ){
        parent.resize(n+1);
        rank.resize(n+1);
        for( int i =1; i <= n; i++){
            parent[i] = i;
            rank[i] = 1;
        }
    }

    
    int findParent(int x){
        if( parent[x] != x){
            parent[x] = findParent(parent[x]);
        }

        return parent[x];
    }

    void unite(int x, int y){
        int parentA =  findParent(x);
        int parentB = findParent(y);

        if( parentA != parentB){
            if(rank[parentA] > rank[parentB]){
                parent[parentB] = parentA;
            }
            else if(rank[parentB] > rank[parentA]){
                parent[parentA] = parentB;
            }
            else{
                parent[parentA] = parentB;
                rank[parentB]++;
            }
        }
    }
};

void buildGraph(int n, int threshold, DSU &dsu) {
    for (int z = threshold + 1; z <= n; z++) {  // Start from threshold + 1
        for (int x = z; x <= n; x += z) {
            if (x + z <= n)
                dsu.unite(x, x + z);
        }
    }
}

int main() {
    int n = 10, threshold = 2;  // Example
    DSU dsu(n);
    
    buildGraph(n, threshold, dsu);

    vector<pair<int, int>> queries = {{3, 9}, {4, 8}, {5, 10}};
    for (auto [src, target] : queries) {
        if (dsu.findParent(src) == dsu.findParent(target))
            cout << "YES\n";
        else
            cout << "NO\n";
    }

    return 0;
}
