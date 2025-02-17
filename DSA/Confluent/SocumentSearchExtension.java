import java.util.*;
import java.util.regex.*;

public class BooleanSearchEngine {
    // Inverted index: word -> (docId -> list of positions in the document)
    private final Map<String, Map<Integer, List<Integer>>> index = new HashMap<>();
    // Stores original documents by docId
    private final Map<Integer, String> documents = new HashMap<>();

    // Add document to the index
    public void addDocument(int docId, String content) {
        documents.put(docId, content);
        String[] words = content.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            index.computeIfAbsent(words[i], k -> new HashMap<>())
                 .computeIfAbsent(docId, k -> new ArrayList<>())
                 .add(i);  // Store word positions
        }
    }

    // Tokenize search query
    private List<String> tokenize(String query) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"[^\"]+\"|\\(|\\)|\\bAND\\b|\\bOR\\b|\\w+").matcher(query.toLowerCase());
        while (matcher.find()) tokens.add(matcher.group());
        return tokens;
    }

    // Search documents based on Boolean query
    public List<Integer> search(String query) {
        List<String> tokens = tokenize(query);
        return new ArrayList<>(evaluateExpression(tokens));
    }

    // Evaluate Boolean expression using two stacks (Shunting-Yard Algorithm)
    private Set<Integer> evaluateExpression(List<String> tokens) {
        Stack<Set<Integer>> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        Map<String, Integer> precedence = Map.of("OR", 1, "AND", 2);

        for (String token : tokens) {
            if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processOperator(operandStack, operatorStack.pop());
                }
                operatorStack.pop(); // Remove '('
            } else if (token.equals("AND") || token.equals("OR")) {
                while (!operatorStack.isEmpty() && precedence.getOrDefault(operatorStack.peek(), 0) >= precedence.get(token)) {
                    processOperator(operandStack, operatorStack.pop());
                }
                operatorStack.push(token);
            } else {
                operandStack.push(getDocumentsContaining(token));
            }
        }

        while (!operatorStack.isEmpty()) {
            processOperator(operandStack, operatorStack.pop());
        }

        return operandStack.isEmpty() ? new HashSet<>() : operandStack.pop();
    }

    // Get document IDs that contain the given word or phrase
    private Set<Integer> getDocumentsContaining(String phrase) {
        // If the phrase is enclosed in double quotes, treat it as a multi-word phrase
        if (phrase.startsWith("\"") && phrase.endsWith("\"")) {
            return searchPhrase(phrase.substring(1, phrase.length() - 1));
        }
        // If it's a single word, just look up the inverted index
        return index.containsKey(phrase) ? index.get(phrase).keySet() : new HashSet<>();
    }

    // Search for phrase in documents
    private Set<Integer> searchPhrase(String phrase) {
        String[] words = phrase.split("\\s+");
        if (words.length == 0 || !index.containsKey(words[0])) return new HashSet<>();

        // Find documents that contain the first word of the phrase
        Set<Integer> possibleDocs = new HashSet<>(index.get(words[0]).keySet());
        Set<Integer> result = new HashSet<>();

        // Check each document if the entire phrase exists in order
        for (int docId : possibleDocs) {
            if (existsInDocument(docId, words)) {
                result.add(docId);
            }
        }
        return result;
    }

    // Check if the phrase exists in a given document in order
    private boolean existsInDocument(int docId, String[] words) {
        List<Integer> firstWordPositions = index.get(words[0]).get(docId);
        for (int start : firstWordPositions) {
            boolean found = true;
            for (int i = 1; i < words.length; i++) {
                if (!index.containsKey(words[i]) || !index.get(words[i]).containsKey(docId)) {
                    found = false;
                    break;
                }
                // Ensure each subsequent word appears at the expected position
                if (!index.get(words[i]).get(docId).contains(start + i)) {
                    found = false;
                    break;
                }
            }
            if (found) return true;
        }
        return false;
    }

    // Process AND/OR operators
    private void processOperator(Stack<Set<Integer>> operandStack, String operator) {
        if (operandStack.size() < 2) return;
        Set<Integer> right = operandStack.pop();
        Set<Integer> left = operandStack.pop();
        operandStack.push(operator.equals("AND") ? intersect(left, right) : union(left, right));
    }

    private Set<Integer> intersect(Set<Integer> a, Set<Integer> b) {
        a.retainAll(b);
        return a;
    }

    private Set<Integer> union(Set<Integer> a, Set<Integer> b) {
        a.addAll(b);
        return a;
    }

    // Main method for testing
    public static void main(String[] args) {
        BooleanSearchEngine engine = new BooleanSearchEngine();
        engine.addDocument(1, "the quick brown fox jumps over the lazy dog");
        engine.addDocument(2, "the quick blue fox runs fast");
        engine.addDocument(3, "a brown dog is quick and clever");
        engine.addDocument(4, "the fast fox is clever");
        engine.addDocument(5, "the quick brown cat and quick brown fox");

        System.out.println(engine.search("\"quick brown\" AND fox"));   // [1, 5]
        System.out.println(engine.search("\"quick brown fox\" OR dog")); // [1, 5, 3]
        System.out.println(engine.search("fox AND (\"quick brown\" OR fast)")); // [1, 2, 4, 5]
        System.out.println(engine.search("\"fast fox\" AND clever")); // [4]
        System.out.println(engine.search("\"quick blue\" OR \"brown dog\"")); // [2, 3]
    }
}
