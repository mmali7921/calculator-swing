import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new GridBagLayout());


        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 50));
        frame.add(textField);




        JButton button7 = new JButton("7");
        frame.add(button7);

        JButton button8 = new JButton("8");
        frame.add(button8);

        JButton button9 = new JButton("9");
        frame.add(button9);

        JButton buttonMultiply = new JButton("*");
        frame.add(buttonMultiply);



        JButton button4 = new JButton("4");
        frame.add(button4);

        JButton button5 = new JButton("5");
        frame.add(button5);

        JButton button6 = new JButton("6");
        frame.add(button6);

        JButton buttonMinus = new JButton("-");
        frame.add(buttonMinus);



        JButton button1 = new JButton("1");
        frame.add(button1);

        JButton button2 = new JButton("2");
        frame.add(button2);

        JButton button3 = new JButton("3");
        frame.add(button3);

        JButton buttonPlus = new JButton("+");
        frame.add(buttonPlus);



        JButton button0 = new JButton("0");
        frame.add(button0);

        JButton buttonDivide = new JButton("/");
        frame.add(buttonDivide);

        JButton buttonEqual = new JButton("=");
        frame.add(buttonEqual);

        JButton buttonClear = new JButton("AC");
        frame.add(buttonClear);


        frame.setVisible(true);
    }

}