package solution;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class Calculator2 implements ActionListener
{
    private JFrame frame;
    private JPanel buttonPanel;
    private JButton button1;
    private JButton button2;

    public Calculator2()
    {
        frame = new JFrame();
        frame.setTitle("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        initialize();
        frame.pack();
        frame.setVisible(true);
    }

    public void initialize()
    {
        buttonPanel = new JPanel();
        button1 = new JButton("Calculate");
        button1.setName("calculateButton");
        button2 = new JButton("Clear");
        button2.setName("clearButton");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        frame.add(buttonPanel, BorderLayout.PAGE_END);
        JPanel resultPanel = new JPanel();
        JLabel resultLabel = new JLabel();
        resultLabel.setName("resultLabel");
        resultLabel.setText("Result = ");
        resultPanel.add(resultLabel);
        frame.add(resultPanel, BorderLayout.LINE_START);
        JPanel textPanel = new JPanel();
        JTextField text1 = new JTextField(10);
        text1.setName("infixExpression");
        textPanel.add(text1);
        frame.add(textPanel, BorderLayout.PAGE_START);
        button1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    ExpressionEvaluator eval = new ExpressionEvaluator();
                    String postFix = eval.toPostfix(text1.getText());
                    double answer = eval.evaluate(postFix);
                    resultLabel.setText(String.format("Result = %f", answer));
                }
                catch (NumberFormatException ex) {
                    resultLabel.setText("Result = Error");
                }
                catch (NoSuchElementException exc) {
                    resultLabel.setText("Result = Error");
                }
                catch (NullPointerException exce) {
                    resultLabel.setText("Result = Error");
                }
                catch (EmptyStackException excep) {
                    resultLabel.setText("Result = Error");
                }
                catch (IllegalArgumentException ex) {
                    resultLabel.setText("Result = Error");
                }
                }
        });
        button2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    text1.setText("");
                    resultLabel.setText("Result = ");
                }
                catch (NumberFormatException ex) {
                    resultLabel.setText("Result = Error");
                }
            }
        });
    }

    public static void main(String[] args)
    {
        //Calculator2 calc = new Calculator2();
    }

    public JFrame getFrame()
    {
        return this.frame;
    }

    public void actionPerformed(ActionEvent event)
    {
        ;
    }
}