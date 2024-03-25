package com.example.calculiator;

import java.util.Stack;

public class Calculator {

    public static double calculate(String expression) {
        expression = expression.replaceAll("EXP", String.valueOf(EXP))
                .replaceAll("e", String.valueOf(E))
                .replaceAll("π", String.valueOf(PI))
                .replaceAll(" ", ""); // Удаляем все пробелы из выражения

        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder numBuilder = new StringBuilder(); // Для сбора чисел

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                numBuilder.append(c); // Собираем число
            } else {
                if (numBuilder.length() > 0) {
                    operands.push(Double.parseDouble(numBuilder.toString()));
                    numBuilder.setLength(0); // Сбрасываем StringBuilder
                }

                if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (operators.peek() != '(') {
                        operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
                    }
                    operators.pop();
                } else if (isOperator(c)) {
                    while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                        operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
                    }
                    operators.push(c);
                } else if (Character.isLetter(c)) {
                    StringBuilder funcBuilder = new StringBuilder();
                    funcBuilder.append(c); // Начинаем сборку функции
                    while (i + 1 < expression.length() && Character.isLetter(expression.charAt(i + 1))) {
                        funcBuilder.append(expression.charAt(++i)); // Добавляем буквы к имени функции
                    }
                    double argument = operands.pop();
                    double result;
                    if (funcBuilder.toString().equals("sin")) {
                        result = Math.sin(Math.toRadians(argument)); // Преобразуем угол в радианы для тригонометрических функций
                    } else if (funcBuilder.toString().equals("cos")) {
                        result = Math.cos(Math.toRadians(argument));
                    } else if (funcBuilder.toString().equals("tan")) {
                        result = Math.tan(Math.toRadians(argument));
                    } else if (funcBuilder.toString().equals("cot")) {
                        result = 1 / Math.tan(Math.toRadians(argument));
                    } else if (funcBuilder.toString().equals("sqrt")) {
                        result = Math.sqrt(argument);
                    } else if (funcBuilder.toString().equals("ln")) {
                        result = Math.log(argument);
                    } else if (funcBuilder.toString().equals("log")) {
                        result = Math.log10(argument);
                    } else if (funcBuilder.toString().equals("abs")) {
                        result = Math.abs(argument);
                    } else if (funcBuilder.toString().equals("!")) {
                        result = factorial(argument);
                    } else {
                        throw new IllegalArgumentException("Unknown function: " + funcBuilder.toString());
                    }
                    operands.push(result);
                }
            }
        }

        if (numBuilder.length() > 0) {
            operands.push(Double.parseDouble(numBuilder.toString()));
        }

        while (!operators.isEmpty()) {
            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
        }

        return operands.pop();
    }

    // Метод для применения оператора к операндам
    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero!");
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    // Метод для определения приоритета операторов
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '^' && (op1 == '*' || op1 == '/')) || (op2 != '^' && (op2 == '+' || op2 == '-'));
    }

    // Метод для проверки, является ли символ оператором
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Метод для вычисления функций
    public static double calculateFunction(String functionName, double argument) {
        switch (functionName) {
            case "sin":
                return Math.sin(argument);
            case "cos":
                return Math.cos(argument);
            case "tan":
                return Math.tan(argument);
            case "cot":
                return 1 / Math.tan(argument); // котангенс равен 1/tan(x)
            case "sqrt":
                return Math.sqrt(argument);
            case "ln":
                return Math.log(argument);
            case "log":
                return Math.log10(argument);
            case "abs":
                return Math.abs(argument);
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

    // Метод для вычисления факториала
    public static double factorial(double n) {
        if (n < 0) throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        if (n == 0 || n == 1) return 1;
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Константы
    public static final double PI = Math.PI;
    public static final double E = Math.E;

    public static final double EXP = Math.exp(1);
}