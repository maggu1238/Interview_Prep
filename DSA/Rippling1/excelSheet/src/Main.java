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

                if (visitedCells.contains(referencedCell)) {
                    throw new RuntimeException("Circular reference detected");
                }

                dependencies.computeIfAbsent(currentCell, k -> new HashSet<>()).add(referencedCell);
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
    if (op == '+' || op == '-') return 1;  // Lowest precedence
    if (op == '*' || op == '/') return 2;  // Higher precedence
    return 0; // Default case (should never happen)
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
////////////////////////////////////////////
////////////////////////////////////////////






import java.util.*;

interface IEvaluator {
    int evaluate(String expression, String currentCell, Map<String, Cell> cells, Map<String, Set<String>> dependencies, Set<String> visitedCells);
}

class BasicEvaluator implements IEvaluator {
    @Override
    public int evaluate(String expression, String currentCell, Map<String, Cell> cells, Map<String, Set<String>> dependencies, Set<String> visitedCells) {
        if (expression.startsWith("=")) {
            return evaluateExpression(expression.substring(1), currentCell, cells, dependencies, visitedCells);
        }
        return parseValue(expression);
    }

    private int evaluateExpression(String expr, String currentCell, Map<String, Cell> cells, Map<String, Set<String>> dependencies, Set<String> visitedCells) {
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
                    throw new RuntimeException("Circular reference detected!");
                }

                // Register dependency
                dependencies.computeIfAbsent(currentCell, k -> new HashSet<>()).add(referencedCell);

                visitedCells.add(referencedCell);
                int value = cells.getOrDefault(referencedCell, new Cell("0")).getCachedOrComputedValue(cells, dependencies, visitedCells);
                values.push(value);
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

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
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

class Cell {
    private String rawValue;
    private Integer cachedValue; // Stores computed value

    public Cell(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
        this.cachedValue = null; // Invalidate cache when value changes
    }

    public int getCachedOrComputedValue(Map<String, Cell> cells, Map<String, Set<String>> dependencies, Set<String> visitedCells) {
        if (cachedValue == null) {
            cachedValue = new BasicEvaluator().evaluate(rawValue, "", cells, dependencies, visitedCells);
        }
        return cachedValue;
    }

    public void invalidateCache() {
        cachedValue = null; // Reset cached value when dependencies update
    }
}

class ExcelSheet {
    private final Map<String, Cell> cells = new HashMap<>();
    private final Map<String, Set<String>> dependencies = new HashMap<>();
    private final IEvaluator evaluator;

    public ExcelSheet(IEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void set(String cell, String value) {
        cells.putIfAbsent(cell, new Cell(value));
        cells.get(cell).setRawValue(value); // Updates value & invalidates cache
        dependencies.put(cell, new HashSet<>()); // Reset dependencies
        
        try {
            getCachedOrComputedValue(cell, new HashSet<>());
            updateDependents(cell);
        } catch (RuntimeException e) {
            System.out.println("ERROR: Circular reference detected for " + cell);
            cells.remove(cell);
            dependencies.remove(cell);
        }
    }

    public void reset(String cell) {
        cells.remove(cell);
        dependencies.remove(cell);
        updateDependents(cell);
    }

    public void print() {
        for (String cell : cells.keySet()) {
            String raw = cells.get(cell).getRawValue();
            String computedValue;
            try {
                computedValue = String.valueOf(getCachedOrComputedValue(cell, new HashSet<>()));
            } catch (RuntimeException e) {
                computedValue = "ERROR";
            }
            System.out.println(cell + " : " + raw + " = " + computedValue);
        }
    }

    private int getCachedOrComputedValue(String cell, Set<String> visitedCells) {
        if (!cells.containsKey(cell)) return 0;
        return cells.get(cell).getCachedOrComputedValue(cells, dependencies, visitedCells);
    }

    private void updateDependents(String cell) {
        if (!dependencies.containsKey(cell)) return;

        for (String dependent : dependencies.get(cell)) {
            cells.get(dependent).invalidateCache(); // Invalidate dependent cells
            try {
                getCachedOrComputedValue(dependent, new HashSet<>());
                updateDependents(dependent);
            } catch (RuntimeException e) {
                System.out.println("ERROR: Circular dependency affected " + dependent);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ExcelSheet sheet = new ExcelSheet(new BasicEvaluator());

        sheet.set("A1", "10");
        sheet.set("B2", "=A1+5");
        sheet.set("C3", "=B2*2");
        sheet.print();

        System.out.println("\nUpdating A1:");
        sheet.set("A1", "20");
        sheet.print(); // B2 and C3 should update automatically

        System.out.println("\nAdding a cycle:");
        sheet.set("A1", "=B1");
        sheet.set("B1", "=A1");
        sheet.print(); // Should print ERROR for A1 and B1
    }
}
