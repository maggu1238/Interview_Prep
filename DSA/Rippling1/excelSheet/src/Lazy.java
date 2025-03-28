import java.util.*;

class ExcelSheet {
    private final Map<String, String> rawValues = new HashMap<>(); // Stores raw input (numbers or formulas)
    
    public void set(String cell, String value) {
        rawValues.put(cell, value);
    }
    
    public void reset(String cell) {
        rawValues.remove(cell);
    }
    
    public void print() {
        for (String cell : rawValues.keySet()) {
            try {
                String raw = rawValues.get(cell);
                String computedValue = String.valueOf(evaluate(raw, new HashSet<>()));
                System.out.println(cell + " : " + raw + " = " + computedValue);
            } catch (RuntimeException e) {
                System.out.println(cell + " : " + rawValues.get(cell) + " = ERROR (Cycle Detected)");
            }
        }
    }
    
    private int evaluate(String expr, Set<String> visitedCells) {
        if (expr.startsWith("=")) {
            return evaluateExpression(expr.substring(1), visitedCells);
        }
        return parseValue(expr, visitedCells);
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
                values.push(parseValue(cellRef.toString(), visitedCells));
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
        if (op == '*' || op == '/') return 2;
        if (op == '+' || op == '-') return 1;
        return 0;
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
    
    private int parseValue(String token, Set<String> visitedCells) {
        if (visitedCells.contains(token)) {
            throw new RuntimeException("Circular Reference Detected: " + token);
        }

        if (rawValues.containsKey(token)) {
            visitedCells.add(token);
            int result = evaluate(rawValues.get(token), visitedCells); // Resolve cell reference
            visitedCells.remove(token);
            return result;
        }
        
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
