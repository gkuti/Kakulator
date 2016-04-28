package com.andela.gkuti.kakulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExpressionEvaluator {
    static ArrayList<String> result = new ArrayList<>();

    public static double evaluate(String buffer) {
        String items[] = buffer.split(" ");
        ArrayList expression = new ArrayList<String>(Arrays.asList(items));
        reduce(expression);
        return Double.parseDouble(result.get(0));
    }

    public static void reduce(ArrayList<String> express) {
        multiplyOperation(express);
    }

    public static void multiplyOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("*")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("*")) {
                    if (first == 0) {
                        int operatorIndex = expression.indexOf(item);
                        double result = eval(expression, operatorIndex, "*");
                        if (operatorIndex > 3) {
                            if (expression.get(operatorIndex - 2).equals("-")) {
                                result = eval(expression, operatorIndex, "*") * -1;
                                generateNegativeExpression(newExpression,result);
                            }
                        } else {
                            newExpression = generateExpression(newExpression, result);
                        }
                        iterator.next();
                        first = 1;
                    } else {
                        newExpression.add(item);
                    }
                } else {
                    newExpression.add(item);
                }
            }
            if (newExpression.contains("*")) {
                multiplyOperation(newExpression);
            } else {
                divisionOperation(newExpression);
            }
        } else {
            divisionOperation(expression);
        }
    }

    public static void divisionOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("/")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("/")) {
                    if (first == 0) {
                        int operatorIndex = expression.indexOf(item);
                        double result = eval(expression, operatorIndex, "/");
                        if (operatorIndex > 3) {
                            if (expression.get(operatorIndex - 2).equals("-")) {
                                result = eval(expression, operatorIndex, "/") * -1;
                                generateNegativeExpression(newExpression, result);
                            }
                        } else {
                            generateExpression(newExpression, result);
                        }
                        iterator.next();
                        first = 1;
                    } else {
                        newExpression.add(item);
                    }
                } else {
                    newExpression.add(item);
                }
            }
            if (newExpression.contains("/")) {
                divisionOperation(newExpression);
            } else {
                addOperation(newExpression);
            }
        } else {
            addOperation(expression);
        }
    }

    public static void substractOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("-")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("-")) {
                    if (first == 0) {
                        int operatorIndex = expression.indexOf(item);
                        double result = eval(expression, operatorIndex, "-");
                        newExpression = generateExpression(newExpression, result);
                        iterator.next();
                        first = 1;
                    } else {
                        newExpression.add(item);
                    }
                } else {
                    newExpression.add(item);
                }
            }
            if (newExpression.contains("-")) {
                substractOperation(newExpression);
            } else {
                result = newExpression;
            }
        } else {
            result = expression;
        }
    }

    public static void addOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("+")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("+")) {
                    if (first == 0) {
                        int operatorIndex = expression.indexOf(item);
                        double result = eval(expression, operatorIndex, "+");
                        newExpression = generateExpression(newExpression, result);
                        iterator.next();
                        first = 1;
                    } else {
                        newExpression.add(item);
                    }
                } else {
                    newExpression.add(item);
                }
            }
            if (newExpression.contains("+")) {
                addOperation(newExpression);
            } else {
                substractOperation(newExpression);
            }
        } else {
            substractOperation(expression);
        }
    }

    public static double eval(ArrayList<String> expression, int operatorIndex, String operator) {
        double result = 0;
        double operand1 = Double.parseDouble(expression.get(operatorIndex - 1));
        double operand2 = Double.parseDouble(expression.get(operatorIndex + 1));
        switch (operator) {
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
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

    public static ArrayList generateNegativeExpression(ArrayList<String> newExpression, double result) {
        newExpression.remove(newExpression.size() - 1);
        newExpression.remove(newExpression.size() - 1);
        newExpression.add("+");
        newExpression.add(String.valueOf(result));
        return newExpression;
    }

    public static ArrayList generateExpression(ArrayList<String> newExpression, double result) {
        newExpression.remove(newExpression.size() - 1);
        newExpression.add(String.valueOf(result));
        return newExpression;
    }
}
