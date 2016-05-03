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
     * @param express the expression to be reduced
     */
    private static void reduce(ArrayList<String> express) {
        simplify(express, "*", "/");
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
    private static ArrayList<String> simplify(ArrayList<String> expression, String operator1, String operator2) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains(operator1) || expression.contains(operator2)) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals(operator1) || item.equals(operator2)) {
                    if (first == 0) {
                        int operatorIndex = expression.indexOf(item);
                        double result = eval(expression, operatorIndex, item, false);
                        if (operatorIndex > 2) {
                            if (expression.get(operatorIndex - 2).equals("-")) {
                                result = eval(expression, operatorIndex, item, true);
                            }
                        }
                        newExpression = generateExpression(expression, newExpression, result, operatorIndex);
                        iterator.next();
                        first = 1;
                    } else {
                        newExpression.add(item);
                    }
                } else {
                    newExpression.add(item);
                }
            }
            result = newExpression;
            if (expression.contains(operator1) || expression.contains(operator2)) {
                simplify(newExpression, operator1, operator2);
            } else {
                return result;
            }
        } else {
            result = expression;
            return result;
        }
        return null;
    }

    /**
     * the method evaluate a given expression
     *
     * @param expression the expression to be evaluated
     * @param operatorIndex the index of the operator
     * @param operator the operator of the operation to be performed
     * @param negative check whether the result should return a negative value
     * @return double value of the result
     */
    private static double eval(ArrayList<String> expression, int operatorIndex, String operator, boolean negative) {
        double result = 0;
        double operand1 = Double.parseDouble(expression.get(operatorIndex - 1));
        double operand2 = Double.parseDouble(expression.get(operatorIndex + 1));
        switch (operator) {
            case "*":
                result = operand1 * operand2;
                if (negative) {
                    result *= -1;
                }
                break;
            case "/":
                result = operand1 / operand2;
                if (negative) {
                    result *= -1;
                }
                break;
            case "+":
                if (negative) {
                    result = -operand1 + operand2;
                } else {
                    result = operand1 + operand2;
                }
                break;
            case "-":
                if (negative) {
                    result = -operand1 - operand2;
                } else {
                    result = operand1 - operand2;
                }
                break;
        }
        return result;
    }

    /**
     * Generates a new expression after calculations
     * @param expression list to pick values from
     * @param newExpression list to add new values
     * @param result result of the last calculation to add to the expression
     * @param operatorIndex index of operator
     * @return
     */
    private static ArrayList<String> generateExpression(ArrayList<String> expression, ArrayList<String> newExpression, double result, int operatorIndex) {
        newExpression.remove(newExpression.size() - 1);
        if (operatorIndex > 2) {
            if (expression.get(operatorIndex - 2).equals("-")) {
                newExpression.remove(newExpression.size() - 1);
                newExpression.add("+");
            }
        }
        newExpression.add(String.valueOf(result));
        return newExpression;
    }
}
