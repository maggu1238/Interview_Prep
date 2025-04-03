import java.util.*;

class Cell {
    private final String rawValue;
    private int computedValue;
    private boolean isError; // Mark if there's a cycle

    public Cell(String rawValue) {
        this.rawValue = rawValue;
        this.isError = false;
    }

    public String getRawValue() {
        return rawValue;
    }

    public int getComputedValue() {
        return isError ? Integer.MIN_VALUE : computedValue; // ERROR value
    }

    public void setComputedValue(int computedValue) {
        this.computedValue = computedValue;
        this.isError = false;
    }

    public void setError() {
        this.isError = true;
    }

    public boolean hasError() {
        return isError;
    }
}

class ExcelSheet {
    private final Map<String, Cell> cells = new HashMap<>();
    private final Map<String, Set<String>> dependents = new HashMap<>();

    public void set(String cell, String value) {
        cells.put(cell, new Cell(value));
        dependents.putIfAbsent(cell, new HashSet<>()); // Ensure entry exists

        // Detect cycles and compute values
        Set<String> visitedCells = new HashSet<>();
        if (!computeValue(cell, visitedCells)) {
            System.out.println("ERROR: Circular reference detected at " + cell);
        }
        updateDependents(cell);
    }

    public void reset(String cell) {
        cells.remove(cell);
        dependents.remove(cell);
        updateDependents(cell);
    }

    public void print() {
        for (String cell : cells.keySet()) {
            String raw = cells.get(cell).getRawValue();
            String computed = cells.get(cell).hasError() ? "ERROR" : String.valueOf(cells.get(cell).getComputedValue());
            System.out.println(cell + " : " + raw + " = " + computed);
        }
    }

    private boolean computeValue(String cell, Set<String> visitedCells) {
        if (visitedCells.contains(cell)) {
            // Cycle detected
            cells.get(cell).setError();
            return false;
        }

        visitedCells.add(cell);
        Cell c = cells.get(cell);
        if (c == null) return true; // No error, default value is 0

        int computed = evaluateExpression(c.getRawValue(), cell, visitedCells);
        c.setComputedValue(computed);
        visitedCells.remove(cell);
        return true;
    }

    private int evaluateExpression(String expr, String currentCell, Set<String> visitedCells) {
        if (!expr.startsWith("=")) {
            return parseValue(expr);
        }

        expr = expr.substring(1); // Remove '='
        return evaluateWithOperators(expr, currentCell, visitedCells);
    }

    private int evaluateWithOperators(String expr, String currentCell, Set<String> visitedCells) {
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

                // Register dependency
                dependents.computeIfAbsent(referencedCell, k -> new HashSet<>()).add(currentCell);

                if (!computeValue(referencedCell, visitedCells)) {
                    cells.get(currentCell).setError();
                    return Integer.MIN_VALUE; // Return error state
                }

                values.push(cells.getOrDefault(referencedCell, new Cell("0")).getComputedValue());
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
        for (String dependent : dependents.getOrDefault(cell, new HashSet<>())) {
            Set<String> visited = new HashSet<>();
            if (!computeValue(dependent, visited)) {
                cells.get(dependent).setError();
            }
            updateDependents(dependent);
        }
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : 2; // * and / have higher precedence
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
        sheet.set("D4", "=C3/2+A1");
        sheet.print(); // Expected: A1 = 10, B2 = 15, C3 = 30, D4 = 25

        System.out.println("\nUpdating A1:");
        sheet.set("A1", "20"); // Should propagate updates
        sheet.print(); // Expected: A1 = 20, B2 = 25, C3 = 50, D4 = 45

        System.out.println("\nAdding a cycle:");
        sheet.set("A1", "=B1");
        sheet.set("B1", "=A1");
        sheet.print(); // Expected: ERROR for A1 and B1
    }
}
