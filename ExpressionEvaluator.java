package solution;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 
 * @author Austin Blaylock
 * @version 04/05/2023
 * 
 */
public class ExpressionEvaluator 
{

    public static final Pattern UNSIGNED_DOUBLE =
            Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][-+]?\\d+)?.*?");
    public static final Pattern CHARACTER = Pattern.compile("\\S.*?");

    /**
     * Takes an infix expression and converts it to postfix.
     * 
     * @param expression
     *            The infix expression.
     * @return the postfix expression.
     */
    public String toPostfix(String expression)
    {
        Stack<String> stack = new Stack<String>();
        Scanner input = new Scanner(expression);
        String next;
        char symbol;
        String postfixExpression = "";

        while (input.hasNext())
        {
            if (input.hasNext(UNSIGNED_DOUBLE))
            {
                next = input.findInLine(UNSIGNED_DOUBLE);
                postfixExpression += next + " ";
            }
            else
            {
                next = input.findInLine(CHARACTER);
                symbol = next.charAt(0);

                if (symbol == '(') {
                    stack.push(next);
                }
                else if (symbol == '*' || symbol == '/' || symbol == '+' || symbol == '-') {
                    while (!stack.isEmpty() && !stack.peek().equals("(") && 
                        lowerPrecedence(symbol, stack.peek().charAt(0))) {
                            postfixExpression += stack.pop() + " ";
                    }
                    stack.push(next);
                }
                else if (symbol == ')') {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        postfixExpression += stack.pop() + " ";
                    }
                    stack.pop();
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        }
        while(!stack.isEmpty())
        {
            postfixExpression += stack.pop() + " ";
        }
        input.close();
        return postfixExpression;
    }

    /**
     * Evaluates a postfix expression and returns the result.
     * 
     * @param postfixExpression
     *            The postfix expression.
     * @return The result of the expression.
     */
    public double evaluate(String postfixExpression)
    {
        Stack<Double> stack = new Stack<Double>();
        Scanner input = new Scanner(postfixExpression);
        String next;
        char operator;
        double answer = Double.NaN;

        while (input.hasNext())
        {
            if (input.hasNext(UNSIGNED_DOUBLE))
            {
                next = input.findInLine(UNSIGNED_DOUBLE);
                stack.push(Double.parseDouble(next));

            }
            else if (answer != Double.NaN)
            {
                next = input.findInLine(CHARACTER);
                operator = next.charAt(0);
                double operand1;
                double operand2;

                if (operator == '*'){
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    answer = (operand1 * operand2);
                    stack.push(answer);
                }
                else if (operator == '/') {
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    answer = (operand1 / operand2);
                    stack.push(answer);
                }
                else if (operator == '+') {
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    answer = (operand1 + operand2);
                    stack.push(answer);
                }
                else if (operator == '-') {
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    answer = (operand1 - operand2);
                    stack.push(answer);
                }
            }
        }
        input.close();
        if (stack.size() > 1) {
            throw new IllegalArgumentException();
        }
        return stack.pop();

    }

    public boolean lowerPrecedence(char a, char b)
    {
        if (a == '*' || a == '/') {
            if (b == '+' || b=='-') {
                return false;
            }
        }
        return true;
    }

}
