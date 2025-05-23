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
            try {
                String expression = input.toString().replace("^", "**");
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                Object resultObj = engine.eval(expression);
                double result = Double.parseDouble(resultObj.toString());
                display.setText(Double.toString(result));
                DatabaseLogger.logCalculation(input.toString(), result);
                input.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
                input.setLength(0);
            }
        }
    }
}
