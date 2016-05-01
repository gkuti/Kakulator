package com.andela.gkuti.kakulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExpressionEvaluator {
    public static ArrayList<String> result = new ArrayList<>();

    public static double evaluate(String buffer) {
        String items[] = buffer.split(" ");
        ArrayList expression = new ArrayList<String>(Arrays.asList(items));
        reduce(expression);
        return Double.parseDouble(result.get(0));
    }

    public static void reduce(ArrayList<String> express) {
        simplify(express, "*");
        simplify(result, "/");
        simplify(result, "+");
        simplify(result, "-");
    }

    public static ArrayList<String> simplify(ArrayList<String> expression, String operator) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains(operator)) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals(operator)) {
                    if (first == 0) {
                        int operatorIndex = expression.indexOf(item);
                        double result = eval(expression, operatorIndex, operator, false);
                        if (operatorIndex > 2) {
                            if (expression.get(operatorIndex - 2).equals("-")) {
                                result = eval(expression, operatorIndex, operator, true);
                            }
                        }
                        newExpression = generateExpression(newExpression, result, operator);
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
            if (newExpression.contains(operator)) {
                simplify(newExpression, operator);
            } else {
                return result;
            }
        } else {
            return expression;
        }
        return null;
    }

    public static double eval(ArrayList<String> expression, int operatorIndex, String operator, boolean negative) {
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
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
        }
        return result;
    }

    public static ArrayList<String> generateExpression(ArrayList<String> newExpression, double result, String operator) {
        newExpression.remove(newExpression.size() - 1);
        if (operator.equals("*") || operator.equals("/")) {
            newExpression.remove(newExpression.size() - 1);
            newExpression.add("+");
        }
        newExpression.add(String.valueOf(result));
        return newExpression;
    }
}