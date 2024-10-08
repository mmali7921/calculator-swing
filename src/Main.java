import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static String operator = "";
    private static double firstOperand = 0;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        // Create the panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;  // Fill both horizontal and vertical spaces
        gbc.insets = new Insets(5, 5, 5, 5); // Set padding between components

        // Create the display field
        JTextField display1 = new JTextField();

        display1.setEditable(true); // Make the display non-editable
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Make the display span the entire row
        gbc.weightx = 1.0; // Let the display expand horizontally
        gbc.weighty = 0.1; // Allow some vertical expansion
        panel.add(display1, gbc); // Add the display to the panel

        JTextField display2 = new JTextField();

        display2.setEditable(true); // Make the display non-editable
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Make the display span the entire row
        gbc.weightx = 1.0; // Let the display expand horizontally
        gbc.weighty = 0.1; // Allow some vertical expansion
        panel.add(display2, gbc); // Add the display to the panel
       // Define the buttons to be used
        String[] buttons = {
                "C", "+/-", "%", "/",
                "7", "8", "9", "x",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "CE", "0", ".", "="
        };


        gbc.gridwidth = 1;
        gbc.weightx = 0.25; // Set equal horizontal weight for buttons
        gbc.weighty = 0.25; // Set equal vertical weight for buttons
        gbc.gridx = 0; // Start at first column
        gbc.gridy = 2; // Start from second row (first row is for the display)

        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            Color operatorColor = new Color(255, 149, 0); // Orange color for operators
            Color restColor = new Color(75, 90, 99); // Light gray color for other buttons
            Color specialColor = new Color(105, 120, 134); // White color for special buttons

            if (buttons[i].matches("[/x\\-+=]")) {  // Operators
                button.setBackground(operatorColor);
            }
            else if (buttons[i].equals("C") || buttons[i].equals("+/-") || buttons[i].equals("%")){  // Special buttons
                button.setBackground(specialColor);
            }
            else {  // All other buttons
                button.setBackground(restColor);
            }
            panel.add(button, gbc); // Add button to the panel

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = button.getText();

                    switch (text) {
                        case "C":
                            display2.setText("");
                            display1.setText("");
                            operator = "";
                            firstOperand = 0;
                            break;
                        case "=":
                            display1.setText(display2.getText());
                            double secondOperand = Double.parseDouble(display2.getText());
                            double result = calculate(firstOperand, secondOperand, operator);
                            display1.setText(firstOperand + " " + operator.replace("*", "x") + " " + secondOperand + " =");
                            display2.setText(String.valueOf(result));
                            operator = "";
                            break;
                        case "+":
                        case "-":
                        case "x":
                        case "/":
                            display2.setText(display2.getText() + " " + text+" ");
                            operator = text.replace("x", "*");
                            firstOperand = Double.parseDouble(display2.getText().split(" ")[0]);
                            display1.setText(firstOperand + " " + operator.replace("*", "x") + " ");
                            display2.setText("");
                            break;
                        case "+/-":
                            double value = Double.parseDouble(display2.getText());
                            display2.setText(String.valueOf(-value));
                            break;
                        case "%":
                            double percentValue = Double.parseDouble(display2.getText());
                            display2.setText(String.valueOf(percentValue / 100));
                            break;
                        default:
                            display2.setText(display2.getText() + text);

                            break;
                    }
                }
            });

            // Move to the next row after every 4 buttons
            if ((i + 1) % 4 == 0) {
                gbc.gridx = 0; // Reset to first column
                gbc.gridy++; // Move to the next row
            } else {
                gbc.gridx++; // Move to the next column
            }
        }

        // Add panel to the frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private static double calculate(double first, double second, String operator) {
        return switch (operator) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> second != 0 ? first / second : 0; // Handle division by zero
            default -> 0;
        };
    }
}
