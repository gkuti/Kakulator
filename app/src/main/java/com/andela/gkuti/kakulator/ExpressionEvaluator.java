package com.andela.gkuti.kakulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExpressionEvaluator {
    static ArrayList<String> result = new ArrayList<>();

    public static double evaluate (String buffer){
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
                        int operator = expression.indexOf(item);
                        double operand1 = Double.parseDouble(expression.get(operator - 1));
                        double operand2 = Double.parseDouble(expression.get(operator + 1));
                        if (expression.get(operator - 2).equals("-")) {
                            double result = -operand1 * operand2;
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.add("+");
                            newExpression.add(String.valueOf(result));
                        } else {
                            double result = operand1 * operand2;
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.add("+");
                            newExpression.add(String.valueOf(result));
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
                DivisionOperation(newExpression);
            }
        } else {
            DivisionOperation(expression);
        }
    }

    public static void AdditionOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("+")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("+")) {
                    if (first == 0) {
                        int operator = expression.indexOf(item);
                        double operand1 = Double.parseDouble(expression.get(operator - 1));
                        double operand2 = Double.parseDouble(expression.get(operator + 1));
                        double result = operand1 + operand2;
                        newExpression.remove(newExpression.size() - 1);
                        newExpression.add(String.valueOf(result));
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
                AdditionOperation(newExpression);
            } else {
                SubtractionOperation(newExpression);
            }
        } else {
            SubtractionOperation(expression);
        }
    }

    public static void SubtractionOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("-")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("-")) {
                    if (first == 0) {
                        int operator = expression.indexOf(item);
                        double operand1 = Double.parseDouble(expression.get(operator - 1));
                        double operand2 = Double.parseDouble(expression.get(operator + 1));
                        double result = operand1 - operand2;
                        newExpression.remove(newExpression.size() - 1);
                        newExpression.add(String.valueOf(result));
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
                SubtractionOperation(newExpression);
            } else {
                result = newExpression;
            }
        } else {
            result = expression;
        }
    }
    public static void DivisionOperation(ArrayList<String> expression) {
        ArrayList<String> newExpression = new ArrayList<>();
        int first = 0;
        Iterator<String> iterator = expression.iterator();
        if (expression.contains("/")) {
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (item.equals("/")) {
                    if (first == 0) {
                        int operator = expression.indexOf(item);
                        double operand1 = Double.parseDouble(expression.get(operator - 1));
                        double operand2 = Double.parseDouble(expression.get(operator + 1));
                        if (expression.get(operator - 2).equals("-")) {
                            double result = -operand1 / operand2;
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.add("+");
                            newExpression.add(String.valueOf(result));
                        } else {
                            double result = operand1 / operand2;
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.remove(newExpression.size() - 1);
                            newExpression.add("+");
                            newExpression.add(String.valueOf(result));
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
                DivisionOperation(newExpression);
            } else {
                AdditionOperation(newExpression);
            }
        } else {
            AdditionOperation(expression);
        }
    }
}
