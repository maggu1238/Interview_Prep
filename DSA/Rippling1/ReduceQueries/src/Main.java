import java.util.*;

public class Main {
    // DSU class to manage union-find operations
    static class DSU {
        private Map<String, String> parent = new HashMap<>();

        // Find the representative (root) of the set containing 'word'
        public String find(String word) {
            parent.putIfAbsent(word, word);
            if (!parent.get(word).equals(word)) {
                parent.put(word, find(parent.get(word)));
            }
            return parent.get(word);
        }

        // Union the sets containing 'word1' and 'word2'
        public void union(String word1, String word2) {
            String root1 = find(word1);
            String root2 = find(word2);
            if (!root1.equals(root2)) {
                parent.put(root1, root2);
            }
        }
    }

    // Method to reduce the list of queries
    public static List<String> reduceQueries(List<List<String>> synonyms, List<String> queries) {
        DSU dsu = new DSU();

        // Union operation for each synonym pair
        for (List<String> pair : synonyms) {
            dsu.union(pair.get(0), pair.get(1));
        }

        Set<String> uniqueQueries = new HashSet<>();

        // Normalize each query
        for (String query : queries) {
            String[] words = query.split(" ");
            StringBuilder normalizedQuery = new StringBuilder();
            for (String word : words) {
                if (normalizedQuery.length() > 0) {
                    normalizedQuery.append(" ");
                }
                normalizedQuery.append(dsu.find(word));
            }
            uniqueQueries.add(normalizedQuery.toString());
        }

        return new ArrayList<>(uniqueQueries);
    }

    // Example usage
    public static void main(String[] args) {
        List<List<String>> synonyms = Arrays.asList(
                Arrays.asList("get", "bring"),
                Arrays.asList("water", "liquid")
        );
        List<String> queries = Arrays.asList("get water", "bring water");

        List<String> reducedQueries = reduceQueries(synonyms, queries);
        for (String query : reducedQueries) {
            System.out.println(query);
        }
    }
}
