/*
You're given a list of elements. Each element has a unique id and 3 properties. Two elements are considered as duplicates if they share any
of the 3 properties. Please write a function that takes the input and returns all the duplicates.


Input:
E1: id1, p1, p2, p3
E2: id2, p1, p4, p5
E3: id3, p6, p7, p8


Output: {{id1, id2}, {id3}}


Input:
E1: id1, p1, p2, p3
E2: id2, p1, p4, p5
E3: id3, p5, p7, p8


Output: {{id1, id3, id3}}

*/


#include <bits/stdc++.h>
using namespace std;

class UnionFind {
    map<string, string> parent;
    map<string, int> rank;
public:
    
    
    UnionFind() {
        parent = map<string, string>();
        rank = map<string, int>();
    }
	
	void set_parent(string id) {
		parent[id] = id;
	}
	
    string find(string x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    void unionSets(string x, string y) {
        string rootX = find(x);
        string rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
    
    bool inSameComponent(string x, string y) {
        return find(x) == find(y);
    }
};

class Solution {
public:
    
    vector < vector <string> > findComponents(map <string, vector <string>> id_to_property) {
        UnionFind uf;
        set <string> ids;
        map < string, vector <string> > property_to_ids;
        for (auto id: id_to_property) {
        	uf.set_parent(id.first);
            ids.insert(id.first);
            for (auto property: id.second) {
                property_to_ids[property].push_back(id.first);
            }
        }
        for (auto property: property_to_ids) {
            for (int i = 0; i < property.second.size(); i++) {
                for (int j = i + 1; j < property.second.size(); j++) {
                    uf.unionSets(property.second[i], property.second[j]);
                }
            }
        }

        
        map <string, vector <string> > final_mapping;
        for (auto id: ids) {
            final_mapping[uf.find(id)].push_back(id);
        }
        vector < vector <string> > res;
        for (auto x: final_mapping) {
            res.push_back(x.second);
        }
        return res;
    }
};

int main() {
	map <string, vector <string> > id_to_property = {
		{ "id1", { "p1", "p2", "p3" } },
		{ "id2", { "p1", "p4", "p5" } },
		{ "id3", { "p6", "p7", "p8" } }
	};
	Solution sol;
	vector < vector <string> > components = sol.findComponents(id_to_property);
	for (auto c: components) {
		for (string x: c) {
			cout << x << " ";
		}
		cout << endl;
	}
	return 0;
}