package com.andela.gkuti.kakulator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * ExpressionEvaluator class
 */
public class ExpressionEvaluator {
    public static ArrayList<String> result = new ArrayList<>();

    /**
     * Return the result of the expression specified
     *
     * @param buffer the String of expression
     * @return double value of the result
     */
    public static double evaluate(String buffer) {
        String items[] = buffer.split(" ");
        ArrayList expression = new ArrayList<String>(Arrays.asList(items));
        if (expression.size() % 2 == 0) {
            expression.remove(expression.size() - 1);
        }
        reduce(expression);
        return Double.parseDouble(result.get(0));
    }

    /**
     * reduces the expression
     *
     * @param expression the expression to be reduced
     */
    private static void reduce(ArrayList<String> expression) {
        simplify(expression, "*", "/");
        simplify(result, "+", "-");
    }

    /**
     * method simplifies an expression containing the operator passed
     *
     * @param expression the expression to be simplified
     * @param operator1  the first String operator
     * @param operator2  the second String operator
     * @return ArrayList of a simplified expression
     */
    private static void simplify(ArrayList<String> expression, String operator1, String operator2) {
        ArrayList<String> newExpression = new ArrayList<>();
        if (isOperatorPresent(operator1, operator2, expression)) {
            result = performCalculations(operator1, operator2, newExpression, expression);
            if (isOperatorPresent(operator1, operator2, result)) {
                simplify(newExpression, operator1, operator2);
            }
        } else {
            result = expression;
        }
    }

    /**
     * the method evaluate a given expression
     *
     * @param expression    the expression to be evaluated
     * @param operatorIndex the index of the operator
     * @param operator      the operator of the operation to be performed
     * @param negative      check whether the result should return a negative value
     * @return double value of the result
     */
    public static double eval(ArrayList<String> expression, int operatorIndex, String operator, boolean negative) {
        double result = 0;
        double operand1 = Double.parseDouble(expression.get(operatorIndex - 1));
        double operand2 = Double.parseDouble(expression.get(operatorIndex + 1));
        result = newOperand(operand1, operand2, negative, operator);
        return result;
    }

    private static double newOperand(double operand1, double operand2, boolean negative, String operator) {
        double result = 0;
        result = elementaryCalculation(operator, operand1, operand2, negative);
        return result;
    }

    /**
     * Generates a new expression after calculations
     *
     * @param expression    list to pick values from
     * @param newExpression list to add new values
     * @param result        result of the last calculation to add to the expression
     * @param operatorIndex index of operator
     * @return new ArrayList of new expression
     */
    private static ArrayList<String> generateExpression(ArrayList<String> expression, ArrayList<String> newExpression, double result, int operatorIndex) {
        newExpression.remove(newExpression.size() - 1);
        if (containsNegative(operatorIndex, expression)) {
            newExpression.remove(newExpression.size() - 1);
            newExpression.add("+");
        }
        newExpression.add(String.valueOf(result));
        return newExpression;
    }

    /**
     * @param operator1     the first String operator
     * @param operator2     the second String operator
     * @param newExpression list to add new values
     * @param expression    list to pick values from
     * @return new ArrayList of new expression
     */
    private static ArrayList performCalculations(String operator1, String operator2, ArrayList newExpression, ArrayList expression) {
        int reduce = 0;
        Iterator<String> iterator = expression.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (equalsOperator(item, operator1, operator2) && reduce == 0) {
                newExpression = removeOperator(item, expression, newExpression);
                reduce = 1;
                iterator.next();
            } else {
                newExpression.add(item);
            }
        }
        return newExpression;
    }

    /**
     * @param operator      operator to remove
     * @param expression    list to pick values from
     * @param newExpression list to add new values
     * @return new ArrayList of new expressio
     */

    private static ArrayList removeOperator(String operator, ArrayList expression, ArrayList newExpression) {
        int operatorIndex = expression.indexOf(operator);
        double result = eval(expression, operatorIndex, operator, false);
        if (containsNegative(operatorIndex, expression)) {
            result = eval(expression, operatorIndex, operator, true);
        }
        newExpression = generateExpression(expression, newExpression, result, operatorIndex);
        return newExpression;
    }

    /**
     * @param operator the String operator
     * @param operand1 the left hand side number
     * @param operand2 the rigth hand side number
     * @param negative
     * @return check whether the result should return a negative value
     */
    private static double elementaryCalculation(String operator, double operand1, double operand2, boolean negative) {
        double result = 0;
        if (negative) {
            operand1 = -operand1;
        }
        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
        }
        return result;
    }

    public static boolean isOperatorPresent(String operator1, String operator2, ArrayList expression) {
        return (expression.contains(operator1) || expression.contains(operator2));
    }

    public static boolean containsNegative(int operatorIndex, ArrayList expression) {
        return (operatorIndex > 2 && expression.get(operatorIndex - 2).equals("-"));
    }

    public static boolean equalsOperator(String item, String operator1, String operator2) {
        return (item.equals(operator1) || item.equals(operator2));
    }

}