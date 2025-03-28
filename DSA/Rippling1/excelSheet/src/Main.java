import java.util.*;

class ExcelSheet {
    private final Map<String, String> rawValues = new HashMap<>();  // Stores raw input (numbers or formulas)
    private final Map<String, Integer> computedValues = new HashMap<>();  // Stores computed values
    private final Map<String, Set<String>> dependencies = new HashMap<>();  // Tracks dependent cells

    public void set(String cell, String value) {
        rawValues.put(cell, value);
        dependencies.put(cell, new HashSet<>());  // Reset dependencies
        
        try {
            int computed = evaluate(value, new HashSet<>());
            computedValues.put(cell, computed);
            updateDependents(cell);  // Propagate changes
        } catch (RuntimeException e) {
            System.out.println("ERROR: Circular reference detected for " + cell);
            rawValues.remove(cell);
            computedValues.remove(cell);
            dependencies.remove(cell);
        }
    }

    public void reset(String cell) {
        rawValues.remove(cell);
        computedValues.remove(cell); // Remove cached computed value
        updateDependents(cell);
        dependencies.remove(cell);

    }

    public void print() {
        for (String cell : rawValues.keySet()) {
            String raw = rawValues.get(cell);
            String computedValue = computedValues.containsKey(cell) ? String.valueOf(computedValues.get(cell)) : "ERROR";
            System.out.println(cell + " : " + raw + " = " + computedValue);
        }
    }

    private int evaluate(String expr, Set<String> visitedCells) {
        if (expr.startsWith("=")) {
            return evaluateExpression(expr.substring(1), visitedCells);
        }
        return parseValue(expr);
    }

    private int evaluateExpression(String expr, Set<String> visitedCells) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                values.push(num);
                continue;
            }

            if (Character.isLetter(ch)) {
                StringBuilder cellRef = new StringBuilder();
                while (i < expr.length() && Character.isLetterOrDigit(expr.charAt(i))) {
                    cellRef.append(expr.charAt(i));
                    i++;
                }
                String referencedCell = cellRef.toString();

                if (visitedCells.contains(referencedCell)) {
                    throw new RuntimeException("Circular reference detected");
                }

                dependencies.computeIfAbsent(referencedCell, k -> new HashSet<>()).add(referencedCell);
                visitedCells.add(referencedCell);
                values.push(computedValues.getOrDefault(referencedCell, 0));
                visitedCells.remove(referencedCell);
                continue;
            }

            if ("+-*/".indexOf(ch) != -1) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    int b = values.pop();
                    int a = values.pop();
                    values.push(applyOperation(a, b, operators.pop()));
                }
                operators.push(ch);
            }
            i++;
        }

        while (!operators.isEmpty()) {
            int b = values.pop();
            int a = values.pop();
            values.push(applyOperation(a, b, operators.pop()));
        }

        return values.pop();
    }

    private void updateDependents(String cell) {
        for (String dependent : dependencies.getOrDefault(cell, new HashSet<>())) {
            try {
                int newValue = evaluate(rawValues.get(dependent), new HashSet<>());
                computedValues.put(dependent, newValue);
                updateDependents(dependent);
            } catch (RuntimeException e) {
                computedValues.put(dependent, 0); // Mark as error
            }
        }
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : 2;
    }

    private int applyOperation(int a, int b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> (b != 0) ? a / b : 0;
            default -> 0;
        };
    }

    private int parseValue(String token) {
        return token.chars().allMatch(Character::isDigit) ? Integer.parseInt(token) : 0;
    }
}

public class Main {
    public static void main(String[] args) {
        ExcelSheet sheet = new ExcelSheet();
        sheet.set("A1", "10");
        sheet.set("B2", "=A1+5");
        sheet.set("C3", "=B2*2");
        sheet.print();

        System.out.println("\nAdding a cycle:");
        sheet.set("A1", "=B1");
        sheet.set("B1", "=A1");
        sheet.print(); // Should print ERROR for A1 and B1
    }
}
