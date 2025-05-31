import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.*;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder input;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        input = new StringBuilder();

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 20));
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "Clear", "(", ")", "^"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Validates the mathematical expression for basic syntax errors.
     * @param expr The input expression string.
     * @return true if valid, false otherwise.
     */
    private boolean isValidExpression(String expr) {
        // Prevents empty input, consecutive operators, and unbalanced parentheses
        if (expr.isEmpty()) return false;
        if (!areParenthesesBalanced(expr)) return false;
        if (expr.matches(".*[+\\-*/^]{2,}.*")) return false; // consecutive operators
        if (expr.matches(".*[^0-9.()+\\-*/^].*")) return false; // invalid characters
        return true;
    }

    /**
     * Checks if parentheses are balanced in the expression.
     */
    private boolean areParenthesesBalanced(String expr) {
        int count = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') count++;
            if (c == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.()+-*/^".contains(cmd)) {
            input.append(cmd);
            display.setText(input.toString());
        } else if (cmd.equals("Clear")) {
            input.setLength(0);
            display.setText("");
        } else if (cmd.equals("=")) {
            String expr = input.toString();
            if (!isValidExpression(expr)) {
                display.setText("Invalid Input");
                input.setLength(0);
                return;
            }
            try {
                String expression = expr.replace("^", "**");
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                Object resultObj = engine.eval(expression);
                double result = Double.parseDouble(resultObj.toString());
                display.setText(Double.toString(result));
                // Log calculation if DatabaseLogger is available
                try {
                    DatabaseLogger.logCalculation(expr, result);
                } catch (Exception logEx) {
                    // Logging failure should not crash the app
                    System.err.println("Logging failed: " + logEx.getMessage());
                }
                input.setLength(0);
            } catch (ScriptException se) {
                display.setText("Syntax Error");
                input.setLength(0);
            } catch (NumberFormatException nfe) {
                display.setText("Math Error");
                input.setLength(0);
            } catch (Exception ex) {
                display.setText("Error: " + ex.getMessage());
                input.setLength(0);
            }
        }
    }
}
