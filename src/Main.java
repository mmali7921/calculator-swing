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
        frame.setSize(350, 550);
        frame.getContentPane().setBackground(new Color(28, 28, 28));

        // Create the panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(28, 28, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;  // Fill both horizontal and vertical spaces
        gbc.insets = new Insets(8, 8, 8, 8); // Set padding between components

        // Create the display field
        JTextField display1 = new JTextField();
        display1.setEditable(false);
        display1.setBackground(new Color(28, 28, 28));
        display1.setForeground(Color.LIGHT_GRAY);
        display1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        display1.setHorizontalAlignment(JTextField.RIGHT);
        display1.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        gbc.gridwidth = GridBagConstraints.REMAINDER; // Make the display span the entire row
        gbc.weightx = 1.0; // Let the display expand horizontally
        gbc.weighty = 0.05; // Allow some vertical expansion
        panel.add(display1, gbc); // Add the display to the panel

        JTextField display2 = new JTextField();
        display2.setEditable(false);
        display2.setBackground(new Color(28, 28, 28));
        display2.setForeground(Color.WHITE);
        display2.setFont(new Font("SansSerif", Font.BOLD, 48));
        display2.setHorizontalAlignment(JTextField.RIGHT);
        display2.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15;
        panel.add(display2, gbc);

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
            RoundedButton button = new RoundedButton(buttons[i]);
            button.setFont(new Font("SansSerif", Font.PLAIN, 28));
            
            Color operatorColor = new Color(255, 149, 0);      // Orange color for operators
            Color numberColor = new Color(51, 51, 51);         // Dark gray color for number buttons
            Color specialColor = new Color(165, 165, 165);       // Light gray color for special buttons

            if (buttons[i].matches("[/x\\-+=]")) {  // Operators
                button.setBackground(operatorColor);
                button.setForeground(Color.WHITE);
            }
            else if (buttons[i].equals("C") || buttons[i].equals("+/-") || buttons[i].equals("%") || buttons[i].equals("CE")) {  // Special buttons
                button.setBackground(specialColor);
                button.setForeground(Color.BLACK);
            }
            else {  // Number buttons
                button.setBackground(numberColor);
                button.setForeground(Color.WHITE);
            }
            
            // Special styling for wide zero button could go here, but using uniform grid for now.
            
            panel.add(button, gbc); // Add button to the panel

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = button.getText();

                    switch (text) {
                        case "C":
                        case "CE":
                            display2.setText("");
                            display1.setText("");
                            operator = "";
                            firstOperand = 0;
                            break;
                        case "=":
                            if(!display2.getText().isEmpty() && !operator.isEmpty()) {
                                double secondOperand = Double.parseDouble(display2.getText());
                                double result = calculate(firstOperand, secondOperand, operator);
                                // remove .0 if integer
                                String resultStr = result == (long) result ? String.format("%d", (long) result) : String.format("%s", result);
                                String firstOpStr = firstOperand == (long) firstOperand ? String.format("%d", (long) firstOperand) : String.format("%s", firstOperand);
                                String secondOpStr = secondOperand == (long) secondOperand ? String.format("%d", (long) secondOperand) : String.format("%s", secondOperand);
                                
                                display1.setText(firstOpStr + " " + operator.replace("*", "x") + " " + secondOpStr + " =");
                                display2.setText(resultStr);
                                operator = "";
                            }
                            break;
                        case "+":
                        case "-":
                        case "x":
                        case "/":
                            if(!display2.getText().isEmpty()) {
                                String opText = text.replace("x", "*");
                                firstOperand = Double.parseDouble(display2.getText());
                                operator = opText;
                                String firstOpStr = firstOperand == (long) firstOperand ? String.format("%d", (long) firstOperand) : String.format("%s", firstOperand);
                                display1.setText(firstOpStr + " " + text + " ");
                                display2.setText("");
                            } else if (!operator.isEmpty()) {
                                // Just changing the operator
                                operator = text.replace("x", "*");
                                String firstOpStr = firstOperand == (long) firstOperand ? String.format("%d", (long) firstOperand) : String.format("%s", firstOperand);
                                display1.setText(firstOpStr + " " + text + " ");
                            }
                            break;
                        case "+/-":
                            if(!display2.getText().isEmpty()) {
                                double value = Double.parseDouble(display2.getText());
                                String mappedVal = (-value == (long) -value) ? String.format("%d", (long) -value) : String.format("%s", -value);
                                display2.setText(mappedVal);
                            }
                            break;
                        case "%":
                            if(!display2.getText().isEmpty()) {
                                double percentValue = Double.parseDouble(display2.getText());
                                display2.setText(String.valueOf(percentValue / 100));
                            }
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

        // Add padding around the entire panel
        frame.add(panel);
        frame.setLocationRelativeTo(null); // Center on screen
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
