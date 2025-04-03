import java.util.*;

class Cell {
    private String rawValue;
    private int computedValue;

    public Cell(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public int getComputedValue() {
        return computedValue;
    }

    public void setComputedValue(int computedValue) {
        this.computedValue = computedValue;
    }
}

class ExcelSheet {
    private final Map<String, Cell> cells = new HashMap<>();
    private final Map<String, Set<String>> dependents = new HashMap<>();  // Tracks reverse dependencies (cells affected by changes)

    public void set(String cell, String value) {
        cells.put(cell, new Cell(value));  // Store new cell value
        try {
            getComputedValue(cell, new HashSet<>());  // Compute value and check for cycles
            updateDependents(cell);  // Propagate changes
        } catch (RuntimeException e) {
            System.out.println("ERROR: Circular reference detected for " + cell);
            cells.remove(cell);
        }
    }

    public void reset(String cell) {
        cells.remove(cell);
        dependents.remove(cell);
        updateDependents(cell);  // Recompute dependent cells
    }

    public void print() {
        for (String cell : cells.keySet()) {
            String raw = cells.get(cell).getRawValue();
            String computed = cells.containsKey(cell) ? String.valueOf(cells.get(cell).getComputedValue()) : "ERROR";
            System.out.println(cell + " : " + raw + " = " + computed);
        }
    }

    private int getComputedValue(String cell, Set<String> visitedCells) {
        if (visitedCells.contains(cell)) {
            throw new RuntimeException("Circular reference detected");
        }

        visitedCells.add(cell);
        Cell c = cells.get(cell);
        if (c == null) return 0;

        int computed = evaluate(c.getRawValue(), cell, visitedCells);
        c.setComputedValue(computed);
        visitedCells.remove(cell);

        return computed;
    }

    private int evaluate(String expr, String currentCell, Set<String> visitedCells) {
        if (expr.startsWith("=")) {
            return evaluateExpression(expr.substring(1), currentCell, visitedCells);
        }
        return parseValue(expr);
    }

    private int evaluateExpression(String expr, String currentCell, Set<String> visitedCells) {
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

                // Register reverse dependency (currentCell is dependent on referencedCell)
                dependents.computeIfAbsent(referencedCell, k -> new HashSet<>()).add(currentCell);

                values.push(getComputedValue(referencedCell, visitedCells));
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
        if (!dependents.containsKey(cell)) return;
        
        for (String dependent : dependents.get(cell)) {
            try {
                getComputedValue(dependent, new HashSet<>());
                updateDependents(dependent);
            } catch (RuntimeException e) {
                cells.get(dependent).setComputedValue(0); // Mark as error
            }
        }
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : 2; // Multiplication and Division have higher precedence
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

        // Basic value setting
        sheet.set("A1", "10");
        sheet.set("B2", "=A1+5");
        sheet.set("C3", "=B2*2");
        sheet.print();

        // Reset a value and see if it updates correctly
        System.out.println("\nResetting A1:");
        sheet.reset("A1");
        sheet.print();

        // Circular reference detection
        System.out.println("\nAdding a cycle:");
        sheet.set("A1", "=B1");
        sheet.set("B1", "=A1");
        sheet.print(); // Should detect cycle and remove values
    }
}
