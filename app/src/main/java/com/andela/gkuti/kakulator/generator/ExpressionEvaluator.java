package com.andela.gkuti.kakulator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExpressionEvaluator {
    public static ArrayList<String> result = new ArrayList<>();

    public static double evaluate(String buffer) {
        String items[] = buffer.split(" ");
        ArrayList expression = new ArrayList<String>(Arrays.asList(items));
        if (expression.size() % 2 == 0) {
            expression.remove(expression.size() - 1);
        }
        reduce(expression);
        return Double.parseDouble(result.get(0));
    }

    private static void reduce(ArrayList<String> express) {
        simplify(express, "*", "/");
        simplify(result, "+", "-");
    }

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
                        newExpression = generateExpression(expression, newExpression, result, item, operatorIndex);
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

    private static ArrayList<String> generateExpression(ArrayList<String> expression, ArrayList<String> newExpression, double result, String operator, int operatorIndex) {
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
