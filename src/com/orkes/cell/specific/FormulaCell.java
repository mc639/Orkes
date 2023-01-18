package com.orkes.cell.specific;

import com.orkes.cell.common.Cell;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaCell implements Cell {
    private final String formula;
    private final Map<String, Cell> cells;
    private final Set<String> visitedCells;
    Stack<Double> values = new Stack<>();
    Stack<String> operators = new Stack<>();


    public FormulaCell(String formula, Map<String, Cell> cells) {
        this.formula = formula;
        this.cells = cells;
        this.visitedCells = new HashSet<>();
    }

    @Override
    public double getValue() {
        // Parse the formula
        Matcher matcher = Pattern.compile("[A-Z]+[1-9][0-9]*|[0-9]+|[+\\-*/()<>=!&|]").matcher(formula);
        while (matcher.find()) {
            String token = matcher.group();
            if (token.equals("=")) {
                // Do not add the "=" operator to the operator stack
                // and move on to the next token
                continue;
            } else if (token.matches("[A-Z]+[1-9][0-9]*")) {
                if(visitedCells.contains(token))
                    throw new IllegalArgumentException("Circular reference: " + token);
                // Get the cell reference
                Cell cell = cells.get(token);
                if (cell == null) {
                    throw new IllegalArgumentException("Invalid cell reference: " + token);
                }
                visitedCells.add(token);
                values.push(cell.getValue());
                visitedCells.remove(token);
            } else if (token.matches("[0-9]+")) {
                values.push((double) Integer.parseInt(token));
            } else if (token.matches("[+\\-*/()<>=!&|]")) {
                while (!operators.empty() && hasPrecedence(token, operators.peek())) {
                    if (token.matches("[&|]")) {
                        if(!operators.empty()) {
                            String operator = operators.pop();
                            int value = 0;
                            if (operator.equals("&")) {
                                value = (values.pop() != 0) & (values.pop() != 0) ? 1 : 0;
                            } else if (operator.equals("|")) {
                                value = (values.pop() != 0) | (values.pop() != 0) ? 1 : 0;
                            }
                            values.push((double) value);
                        }
                    } else {
                        if(!operators.empty()) {
                            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                        }
                    }
                }
            }
            if (token.matches("[+\\-*/<>=!]")) {
                operators.push(token);
            } else if (token.matches("[&|]")) {
                operators.push(token);
            }             else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.empty() && !operators.peek().equals("(")) {
                    if (operators.peek().matches("[&|]")) {
                        String operator = operators.pop();
                        int value = 0;
                        if (operator.equals("&")) {
                            value = (values.pop() != 0) & (values.pop() != 0) ? 1 : 0;
                        } else if (operator.equals("|")) {
                            value = (values.pop() != 0) | (values.pop() != 0) ? 1 : 0;
                        }
                        values.push((double) value);
                    } else {
                        values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                    }
                }
                if (!operators.empty() && operators.peek().equals("(")) {
                    operators.pop();
                }
            } else if (token.equals("!")) {
                values.push((double) (values.pop() == 0 ? 1 : 0));
            }
        }
        while (!operators.empty()){
            if (operators.peek().matches("[&|]")) {
                String operator = operators.pop();
                int value = 0;
                if (operator.equals("&")) {
                    value = (values.pop() != 0) & (values.pop() != 0) ? 1 : 0;
                } else if (operator.equals("|")) {
                    value = (values.pop() != 0) | (values.pop() != 0) ? 1 : 0;
                }
                values.push((double) value);
            } else {
                values.push(applyOp(operators.pop(), values.pop(), values.pop()));
            }
        }
        return values.pop();
    }

    private boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("(") || op2.equals(")"))
            return false;
        if ((op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-")))
            return false;
        return !op1.matches("[&|]") || !op2.matches("[+\\-*/]");
    }

    private double applyOp(String op, double b, double a) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case "!":
                return (b == 0) ? 1 : 0;
            case ">":
                return (a > b) ? 1 : 0;
            case "<":
                return (a < b) ? 1 : 0;
            case ">=":
                return (a >= b) ? 1 : 0;
            case "<=":
                return (a <= b) ? 1 : 0;
            case "==":
                return (a == b) ? 1 : 0;
            case "&":
                return (a != 0) & (b != 0) ? 1 : 0;
            case "|":
                return (a != 0) | (b != 0) ? 1 : 0;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

}
