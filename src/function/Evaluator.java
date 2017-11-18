package function;

import java.lang.reflect.Array;
import java.util.*;

import static function.Operator.*;
import static function.Symbol.*;

public class Evaluator {
    private static HashMap<String, Token> tokenMap = new HashMap<>();

    static {
        tokenMap.put("+", ADD);
        tokenMap.put("-", SUB);
        tokenMap.put("*", MUL);
        tokenMap.put("/", DIV);
        tokenMap.put("^", EXP);
        tokenMap.put("%", PERCENT);
        tokenMap.put("&", CONCAT);
        tokenMap.put("=", EQ);
        tokenMap.put(">", GT);
        tokenMap.put("<", LT);
        tokenMap.put(">=", GE);
        tokenMap.put("<=", LE);
        tokenMap.put("<>", NEQ);
        tokenMap.put("(", LEFT_P);
        tokenMap.put(")", RIGHT_P);
        tokenMap.put(",", COMMA);
    }

    public static List<String> split(String expression) {
        List<String> stringTokens = Arrays.asList(
                expression.split("(?=[*/^&%(,)\"<]|(?<!<)>|(?<![<>])=|(?<![eE])[-+])|(?<=>(?!=)|=|<(?![>=])|[^-eE&+*/^,(=><][-+]|[\"*/&^,()%])"));
        List<String> newStringTokens = new ArrayList<>();

        boolean doubleQuote = false;
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < stringTokens.size(); ++i) {
            String now = stringTokens.get(i);
            if (doubleQuote) {
                temp.append(now);
                if (now.equals("\"")) {
                    doubleQuote = false;
                    newStringTokens.add(temp.toString());
                    temp.setLength(0);
                }
            } else {
                if (now.equals("\"")) {
                    temp.append(now);
                    doubleQuote = true;
                } else {
                    newStringTokens.add(now);
                }
            }
        }
        return newStringTokens;
    }

    public static List<Token> toPostfix(List<String> infix) throws ParserException {
        Stack<Token> operatorStack = new Stack<>();
        Stack<Function> functionStack = new Stack<>();
        ArrayList<Token> outputQueue = new ArrayList<>();
        for (int i = 0; i < infix.size(); ++i) {
            String s = infix.get(i);
//            System.out.println(s);
            if (tokenMap.containsKey(s)) {
//                System.out.println("s in map");
                Token token = tokenMap.get(s);
//                System.out.println(token);
                if (token instanceof Operator) {
                    while (!operatorStack.empty()) {
                        Token top = operatorStack.peek();
                        if (top instanceof Operator
                                && (((Operator) token).isLeftAssoc() ?
                                ((Operator) top).getPrecedence() >= ((Operator) token).getPrecedence() :
                                ((Operator) top).getPrecedence() > ((Operator) token).getPrecedence()
                        )) {
                            outputQueue.add(operatorStack.pop());
                        } else break;
                    }
                    operatorStack.push(token);
                } else if (token == Symbol.LEFT_P) {
                    String after = (i + 1 < infix.size()) ? infix.get(i + 1) : null;
                    if (operatorStack.peek() instanceof Function) {
                        if (!(tokenMap.containsKey(after) && tokenMap.get(after) == RIGHT_P))
                            functionStack.peek().incArgs();
                    }
                    operatorStack.push(token);
                } else if (token == Symbol.RIGHT_P) {
                    while (!operatorStack.empty() && operatorStack.peek() != Symbol.LEFT_P) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                    if (!operatorStack.empty() && operatorStack.peek() instanceof Function) {
                        outputQueue.add(operatorStack.pop());
                        functionStack.pop();
                    }
                } else if (token == Symbol.COMMA) {
                    if (!functionStack.empty() && functionStack.peek() != null) functionStack.peek().incArgs();
                    while (!operatorStack.empty() && operatorStack.peek() != Symbol.LEFT_P) {
                        outputQueue.add(operatorStack.pop());
                    }
                }
            } else {
                if (FunctionType.contains(s.toUpperCase())) {
                    FunctionType ft = FunctionType.valueOf(s.toUpperCase());
                    Function function = new Function(ft);
                    operatorStack.push(function);
                    functionStack.push(function);
                } else {
                    try {
                        Double d = Double.parseDouble(s);
                        outputQueue.add(new Value<>(d));
                    } catch (NumberFormatException e) {
                        if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"')
                            outputQueue.add(new Value<>(s));
                        else {
                            if (s.matches("[A-Za-z]+[0-9]+")) {
                                outputQueue.add(new CellReference(0, 0));
                            } else if (s.matches("[A-Za-z]+[0-9]+:[A-Za-z]+[0-9]+")) {
                                outputQueue.add(new CellRange(0, 0, 0, 0));
                            } else throw new ParserException("Invalid token: " + s);
                        }
                    }
                }
            }
        }
        while (!operatorStack.empty()) outputQueue.add(operatorStack.pop());
        return outputQueue;
    }

    public static Value evaluate(String expression) throws ParserException {
        ArrayList<String> stringTokens = (ArrayList<String>) split(expression);
        ArrayList<Token> tokens = (ArrayList<Token>) toPostfix(stringTokens);
        Computer c = new Computer(tokens);
        return c.compute();
    }

    private static class Computer {
        private List<Token> postFix;

        Computer(List<Token> postfix) {
            this.postFix = postfix;
        }

        Value compute() throws ParserException {
            return call(postFix.get(postFix.size() - 1));
        }

        Value call(Token token) throws ParserException {
            postFix.remove(postFix.size() - 1);
            if (token instanceof Value) return (Value) token;
            try {
                if (token instanceof Operator) {
                    Operator op = (Operator) token;
                    if (op.getArgs() == 2) {
                        Value rightOperand = call(postFix.get(postFix.size() - 1));
                        Value leftOperand = call(postFix.get(postFix.size() - 1));
                        switch (op) {
                            case ADD:
                                return new Value<>(new Double(leftOperand.getValue().toString())
                                        + new Double(rightOperand.getValue().toString()));
                            case SUB:
                                return new Value<>(new Double(leftOperand.getValue().toString())
                                        - new Double(rightOperand.getValue().toString()));
                            case MUL:
                                return new Value<>(new Double(leftOperand.getValue().toString())
                                        * new Double(rightOperand.getValue().toString()));
                            case DIV:
                                return new Value<>(new Double(leftOperand.getValue().toString())
                                        / new Double(rightOperand.getValue().toString()));
                            case EXP:
                                return new Value<>(Math.pow(new Double(leftOperand.getValue().toString()),
                                        new Double(rightOperand.getValue().toString())));
                            case CONCAT:
                                return new Value<>(leftOperand.getValue().toString()
                                        + rightOperand.getValue().toString());
                            case EQ:
                                return new Value<>(leftOperand.getValue().equals(rightOperand.getValue()));
                            case NEQ:
                                return new Value<>(!leftOperand.getValue().equals(rightOperand.getValue()));
                            case GT:
                                return new Value<>(leftOperand.getValue().compareTo(rightOperand.getValue()) > 0);
                            case LT:
                                return new Value<>(leftOperand.getValue().compareTo(rightOperand.getValue()) < 0);
                            case GE:
                                return new Value<>(leftOperand.getValue().compareTo(rightOperand.getValue()) >= 0);
                            case LE:
                                return new Value<>(leftOperand.getValue().compareTo(rightOperand.getValue()) <= 0);
                        }
                    } else if (op.getArgs() == 1) {
                        Value operand = call(postFix.get(postFix.size() - 1));
                        if (op == PERCENT)
                            return new Value<>(((Double) (operand.getValue())) / 100);
                    }
                }
            } catch (NumberFormatException e) {
                throw new ParserException("Invalid numerical operand for numerical operator");
            }
            if (token instanceof Function) {
                Function function = (Function) token;
                int args = function.getArgs();
                ArrayList<Value> values = new ArrayList<>();
                for (int i = 0; i < args; ++i) {
                    Value arg = call(postFix.get(postFix.size() - 1));
                    values.add(arg);
                }
                switch (function.getFunctionType()) {
                    case SUM:
                        return sum(values);
                    case AVERAGE:
                        return average(values);
                    default:
                        return null;
                }
            }
            return null;
        }

        Value<Double> sum(List<Value> values) {
            Double temp = 0.0;
            for (Value value : values) {
                try {
                    temp += (Double) (value.getValue());
                } catch (Exception e) {
                    temp += 0;
                }
            }
            return new Value<>(temp);
        }

        Value<Double> average(List<Value> values) {
            Double temp = 0.0;
            int amt = 0;
            for (Value value : values) {
                try {
                    temp += (Double) (value.getValue());
                    ++amt;
                } catch (Exception e) {
                    temp += 0;
                }
            }
            return new Value<>(temp / amt);
        }
    }
}
