import java.util.*;
import java.util.regex.*;

class Excel {
    private static class Cell {
        String rawValue; // Original value or formula
        int computedValue; // Computed numeric value

        Cell(String raw, int computed) {
            this.rawValue = raw;
            this.computedValue = computed;
        }
    }

    private final Map<String, Cell> sheet = new HashMap<>();

    // Set a value in the cell
    public void set(String cell, String value) {
        int computedValue = evaluate(value);
        sheet.put(cell, new Cell(value, computedValue));
    }

    // Reset a cell
    public void reset(String cell) {
        sheet.remove(cell);
    }

    // Print all cells with their raw and computed values
    public void print() {
        for (Map.Entry<String, Cell> entry : sheet.entrySet()) {
            System.out.println(entry.getKey() + " -> Raw: " + entry.getValue().rawValue +
                    ", Computed: " + entry.getValue().computedValue);
        }
    }

    // Evaluate a cell value, supporting basic arithmetic and cell references
    private int evaluate(String value) {
        if (!value.startsWith("=")) {
            return Integer.parseInt(value); // Plain integer value
        }

        // Remove '=' and parse expression
        String expr = value.substring(1);
        return evaluateExpression(expr);
    }

    // Evaluate an arithmetic expression with cell references
    private int evaluateExpression(String expr) {

        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        Matcher matcher = Pattern.compile("((?<!\\d)-?\\d+ | [A-Z][0-9]+|[-+*/])").matcher(expr);

        System.out.println(expr);
        while (matcher.find()) {
            String token = matcher.group();
            System.out.println(token);

            if (isNumber(token)) {
                values.push(Integer.parseInt(token));
            } else if (isCellReference(token)) {
                if (sheet.containsKey(token)) {
                    values.push(sheet.get(token).computedValue);
                } else {
                    throw new IllegalArgumentException("Invalid cell reference: " + token);
                }
            } else { // Operator
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token.charAt(0))) {
                    processOperation(values, ops.pop());
                }
                ops.push(token.charAt(0));
            }
        }
         System.out.println("__________________");

        while (!ops.isEmpty()) {
            processOperation(values, ops.pop());
        }

        return values.pop();
    }

    // Helper to check if a token is a number
    private boolean isNumber(String token) {
        return token.matches("-?\\d+");
    }

    // Helper to check if a token is a cell reference (e.g., A1, B2)
    private boolean isCellReference(String token) {
        return token.matches("[A-Z][0-9]+");
    }

    // Get precedence of operators
    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }

    // Apply an operation to the stack values
    private void processOperation(Stack<Integer> values, char op) {
        int b = values.pop();
        int a = values.pop();
        switch (op) {
            case '+': values.push(a + b); break;
            case '-': values.push(a - b); break;
            case '*': values.push(a * b); break;
            case '/': values.push(a / b); break;
        }
    }

    public static void main(String[] args) {
        Excel excel = new Excel();
        excel.set("A1", "10");
        excel.set("B1", "20");
        excel.set("C1", "=A1+B1");  // 10 + 20 = 30
        excel.set("D1", "=C1*2");   // 30 * 2 = 60
        excel.set("E1", "=-1+-10+2"); // -1 + (-10) + 2 = -9
        excel.set("F1", "=D1+E1");  // 60 + (-9) = 51

        excel.print();

        excel.reset("C1");
        excel.print();
    }
}
