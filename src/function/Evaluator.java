package function;

import exception.*;
import exception.UnsupportedOperationException;
import main.Cell;
import main.Table;
import value.*;

import java.util.*;

import static function.Operator.*;
import static function.Symbol.*;

@SuppressWarnings({"Duplicates", "WeakerAccess", "UnusedReturnValue", "unused", "UnusedAssignment"})
public class Evaluator {
    private static HashMap<String, Token> tokenMap = new HashMap<>();
    private static Computer computer;

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
                    boolean leftParenthesesExists = false;
                    while (!operatorStack.empty()) {
                        if(operatorStack.peek() == Symbol.LEFT_P){
                            leftParenthesesExists = true;
                            break;
                        } else outputQueue.add(operatorStack.pop());
                    }
                    if(!leftParenthesesExists)
                        throw new ParserException("Mismatched parentheses");
                    else {
                        operatorStack.pop();
                        if (!operatorStack.empty() && operatorStack.peek() instanceof Function) {
                            outputQueue.add(operatorStack.pop());
                            functionStack.pop();
                        }
                    }
                } else if (token == Symbol.COMMA) {
                    if (!functionStack.empty() && functionStack.peek() != null) functionStack.peek().incArgs();
                    boolean leftParenthesesExists = false;
                    while (!operatorStack.empty()) {
                        if(operatorStack.peek() == Symbol.LEFT_P){
                            leftParenthesesExists = true;
                            break;
                        } else outputQueue.add(operatorStack.pop());
                    }
                    if(!leftParenthesesExists)
                        throw new ParserException("Misplaced comma or mismatched parentheses");
                }
            } else {
                if (FunctionType.contains(s.toUpperCase())) {
                    FunctionType ft = FunctionType.valueOf(s.toUpperCase());
                    Function function = new Function(ft);
                    operatorStack.push(function);
                    functionStack.push(function);
                } else {
                    try {
                        outputQueue.add(new NumberValue(Double.valueOf(s)));
                    } catch (NumberFormatException e) {
                        if (s.toLowerCase().equals("true") || s.toLowerCase().equals("false")) {
                            outputQueue.add(new BooleanValue(Boolean.valueOf(s)));
                        } else if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"')
                            outputQueue.add(new StringValue(s.substring(1, s.length() - 1)));
                        else {
                            if (s.matches("([A-Za-z0-9]+!)?[A-Za-z]+[0-9]")) {
                                outputQueue.add(computer.cellNameToReference(s));
                            } else if (s.matches("([A-Za-z0-9]+!)?[A-Za-z]+[0-9]+:[A-Za-z]+[0-9]+")) {
                                outputQueue.add(computer.cellNameToRange(s));
                            } else {
                                outputQueue.add(new ErrorValue(ErrorType.VALUE));
                            }
                        }
                    }
                }
            }
        }
        while (!operatorStack.empty()) {
            if(operatorStack.peek() == Symbol.LEFT_P)
                throw new ParserException("Mismatched parentheses");
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }

    public static ComparableValue evaluate(Cell cell) throws ParserException {
        computer = new Computer(cell);
        ComparableValue result = evaluate(cell.getUnprocessedValue());
        return result;
    }

    public static ComparableValue evaluate(String expression) throws ParserException {
        if(expression.isEmpty()) return null;
        else if(expression.charAt(0) == '=') {
            expression = expression.substring(1,expression.length());
            ArrayList<String> stringTokens = (ArrayList<String>) split(expression);
            ArrayList<Token> tokens = (ArrayList<Token>) toPostfix(stringTokens);
            computer.setPostFix(tokens);
            return (ComparableValue) computer.compute();
        } else {
            if(expression.toUpperCase().equals("TRUE"))
                return new BooleanValue(true);
            if(expression.toUpperCase().equals("FALSE"))
                return new BooleanValue(false);
            try{
                Double d = Double.valueOf(expression);
                return new NumberValue(d);
            } catch (NumberFormatException e){
                return new StringValue(expression);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static class Computer {
        private Cell tempCell;
        private List<Token> postFix;
        private List<CellReference> newPrecedents;
        private List<CellReference> oldPrecedents;

        Computer(Cell cell) {
            tempCell = cell;
            oldPrecedents = cell.getPrecedents();
            newPrecedents = new ArrayList<>();
        }

        public void setPostFix(List<Token> postFix) {
            this.postFix = postFix;
        }

        Value compute() throws ParserException {
            Value result = null;
            List<CellReference> tempPrecedent = new ArrayList<>(oldPrecedents);
            try {
                result = call(postFix.get(postFix.size() - 1));
            } catch (ParserException e) {
                for (CellReference ref : tempPrecedent)
                    ref.removeDependant(tempCell);
                tempCell.removeAllPrecedents();
                throw e;
            }

            tempPrecedent.removeAll(newPrecedents);
            for (CellReference ref : tempPrecedent) {
                ref.removeDependant(tempCell);
                tempCell.removePrecedent(ref);
            }
            newPrecedents.removeAll(oldPrecedents);
            for (CellReference ref : newPrecedents) {
                ref.addDependant(tempCell);
                tempCell.addPrecedent(ref);
            }
            return result;
        }

        Value call(Token token) throws ParserException {
            postFix.remove(postFix.size() - 1);
            if (token instanceof Value) return (Value) token;
            if (token instanceof Operator) {
                try {
                    Operator op = (Operator) token;
                    if (op.getArgs() == 2) {
                        Value rightOperand = call(postFix.get(postFix.size() - 1));
                        Value leftOperand = call(postFix.get(postFix.size() - 1));
                        if (leftOperand instanceof ErrorValue) return leftOperand;
                        if (rightOperand instanceof ErrorValue) return rightOperand;
                        switch (op) {
                            case ADD:
                                return new NumberValue(((NumberValue) leftOperand).getValue()
                                        + ((NumberValue) rightOperand).getValue());
                            case SUB:
                                return new NumberValue(((NumberValue) leftOperand).getValue()
                                        - ((NumberValue) rightOperand).getValue());
                            case MUL:
                                return new NumberValue(((NumberValue) leftOperand).getValue()
                                        * ((NumberValue) rightOperand).getValue());
                            case DIV:
                                if (((NumberValue) rightOperand).getValue() == 0)
                                    return new ErrorValue(ErrorType.DIVZERO);
                                return new NumberValue(((NumberValue) leftOperand).getValue()
                                        / ((NumberValue) rightOperand).getValue());
                            case EXP:
                                Double left = ((NumberValue) leftOperand).getValue();
                                Double right = ((NumberValue) rightOperand).getValue();
                                if (left < 0 && Math.abs(Math.floor(right) - right) > 1e-9)
                                    return new ErrorValue(ErrorType.NUM);
                                if (left == 0 && right == 0)
                                    return new ErrorValue(ErrorType.NUM);
                                Double ans = Math.pow(left, right);
                                return new NumberValue(ans);
                            case CONCAT:
                                return new StringValue(leftOperand.getValue().toString()
                                        + rightOperand.getValue().toString());
                            case EQ:
                                return new BooleanValue(leftOperand.getValue().equals(rightOperand.getValue()));
                            case NEQ:
                                return new BooleanValue(!leftOperand.getValue().equals(rightOperand.getValue()));
                            case GT:
                                return new BooleanValue(
                                        ((ComparableValue) leftOperand).getValue()
                                                .compareTo(((ComparableValue) rightOperand).getValue()) > 0);
                            case LT:
                                return new BooleanValue(
                                        ((ComparableValue) leftOperand).getValue()
                                                .compareTo(((ComparableValue) rightOperand).getValue()) < 0);
                            case GE:
                                return new BooleanValue(
                                        ((ComparableValue) leftOperand).getValue()
                                                .compareTo(((ComparableValue) rightOperand).getValue()) >= 0);
                            case LE:
                                return new BooleanValue(
                                        ((ComparableValue) leftOperand).getValue()
                                                .compareTo(((ComparableValue) rightOperand).getValue()) <= 0);
                        }
                    } else if (op.getArgs() == 1) {
                        Value operand = call(postFix.get(postFix.size() - 1));
                        if (operand instanceof ErrorValue) return operand;
                        if (op == PERCENT)
                            return new NumberValue(((Double) (operand.getValue())) / 100);
                    }
                } catch (ClassCastException e) {
                    return new ErrorValue(ErrorType.VALUE);
                }
            }
            if (token instanceof Function) {
                Function function = (Function) token;
                int args = function.getArgs();
                ArrayList<Value> values = new ArrayList<>();
                ErrorValue tempError = null;
                for (int i = 0; i < args; ++i) {
                    Value arg = call(postFix.get(postFix.size() - 1));
                    if (arg instanceof ErrorValue) tempError = (ErrorValue) arg;
                    values.add(arg);
                }
                if (tempError != null) return tempError;
                switch (function.getFunctionType()) {
                    case SUM:
                        return sum(values);
                    case AVERAGE:
                        return average(values);
                    case STDDEV:
                        return stddev(values);
                    case VAR:
                        return variance(values);
                    case COUNT:
                        return count(values);
                    case COUNTBLANK:
                        return countblank(values);
                    case MAX:
                        return max(values);
                    case MIN:
                        return min(values);
                    default:
                        return null;
                }
            }
            if (token instanceof CellSingle) {
                newPrecedents.add((CellSingle) token);
                return ((CellSingle) token).getValue();
            }
            if (token instanceof CellRange) {
                newPrecedents.add((CellRange) token);
                return ((CellRange) token).getValue();
            }
            return null;
        }

        NumberValue sum(List<Value> values) {
            Double temp = 0.0;
            for (Value value : values) {
                if (value instanceof NumberValue)
                    temp += (Double) value.getValue();
                else if (value instanceof BooleanValue)
                    temp += (Boolean) value.getValue() ? 1 : 0;
                else if (value instanceof ListValue) {
                    ArrayList<ComparableValue> comparableValues = ((ListValue) value).getValue();
                    for (ComparableValue val : comparableValues) {
                        if (val instanceof NumberValue)
                            temp += (Double) value.getValue();
                        else if (val instanceof BooleanValue)
                            temp += (Boolean) val.getValue() ? 1 : 0;
                    }
                }
            }
            return new NumberValue(temp);
        }

        NumberValue average(List<Value> values) {
            NumberValue sum = sum(values);
            NumberValue count = count(values);
            if (count.getValue() == 0) return new NumberValue(0);
            return new NumberValue(sum.getValue() / count.getValue());
        }

        NumberValue variance(List<Value> values) {
            Double avg = average(values).getValue();
            Double temp = 0.0;
            int amt = count(values).getValue().intValue();
            for (Value value : values) {
                if (value instanceof NumberValue) {
                    temp += ((Double) value.getValue() - avg) * ((Double) value.getValue() - avg);
                } else if (value instanceof ListValue) {
                    ArrayList<ComparableValue> comparableValues = ((ListValue) value).getValue();
                    for (ComparableValue val : comparableValues) {
                        if (val instanceof NumberValue)
                            temp += ((Double) val.getValue() - avg) * ((Double) val.getValue() - avg);
                    }
                }
            }
            return new NumberValue(temp / amt);
        }

        NumberValue stddev(List<Value> values) {
            return new NumberValue(Math.sqrt(variance(values).getValue()));
        }

        NumberValue min(List<Value> values) {
            ArrayList<NumberValue> val = new ArrayList<>();
            for (Value v : values) {
                if (v instanceof NumberValue)
                    val.add((NumberValue) v);
                else if (v instanceof ListValue) {
                    ArrayList<ComparableValue> arr = ((ListValue) v).getValue();
                    for (Value vv : arr) {
                        if (vv instanceof NumberValue)
                            val.add((NumberValue) vv);
                    }
                }
            }
            return val.isEmpty() ? new NumberValue(0) : Collections.min(val);
        }

        NumberValue max(List<Value> values) {
            ArrayList<NumberValue> val = new ArrayList<>();
            for (Value v : values) {
                if (v instanceof NumberValue)
                    val.add((NumberValue) v);
                else if (v instanceof ListValue) {
                    ArrayList<ComparableValue> arr = ((ListValue) v).getValue();
                    for (Value vv : arr) {
                        if (vv instanceof NumberValue)
                            val.add((NumberValue) vv);
                    }
                }
            }
            return val.isEmpty() ? new NumberValue(0) : Collections.max(val);
        }

        NumberValue count(List<Value> values) {
            Integer count = 0;
            for (Value value : values) {
                if (value instanceof NumberValue) ++count;
                else if (value instanceof ListValue) {
                    ArrayList<ComparableValue> comparableValues = ((ListValue) value).getValue();
                    for (ComparableValue val : comparableValues) {
                        if (val instanceof NumberValue) ++count;
                    }
                }
            }
            return new NumberValue(count);
        }

        NumberValue countblank(List<Value> values) throws ParserException {
            if (values.size() != 1) throw new InvalidArgumentException("Too many arguments for this function");
            if (!(values.get(0) instanceof ListValue))
                throw new InvalidArgumentException("Wrong argument for this function");
            return new NumberValue(((ListValue) values.get(0)).size() - count(values).getValue());
        }

        public CellSingle cellNameToReference(String text) throws ParserException {
            String[] sheet_cell = text.split("!");
            int rowNumber, colNumber;
            String cellName, sheetName;
            Table tableRef = null;
            if (sheet_cell.length == 1) {
                cellName = sheet_cell[0];
                tableRef = (Table) tempCell.getParent();
            } else if (sheet_cell.length == 2) {
                throw new UnsupportedOperationException("Referencing from another sheet is not yet supported");
            } else throw new ParserException("Invalid cell name");
            String[] cellTokens = cellName.split("(?<=[A-Za-z])(?=[0-9])");
            if (cellTokens.length != 2) throw new ParserException("Invalid cell name");
            String colLetter = cellTokens[0].toUpperCase();
            rowNumber = Integer.valueOf(cellTokens[1]);
            colNumber = 0;
            for (int i = 0; i < colLetter.length(); ++i) {
                colNumber = colNumber * 26 + (colLetter.charAt(i) - 'A' + 1);
            }
            return new CellSingle(tableRef, colNumber, rowNumber);
        }

        public CellRange cellNameToRange(String text) throws ParserException {
            String[] sheet_cell = text.split("!");
            int rowNumber1, colNumber1;
            int rowNumber2, colNumber2;
            String cellRangeName, sheetName;
            Table tableRef = null;
            if (sheet_cell.length == 1) {
                cellRangeName = sheet_cell[0];
                tableRef = (Table) tempCell.getParent();
            } else if (sheet_cell.length == 2) {
                throw new UnsupportedOperationException("Referencing from another sheet is not yet supported");
            } else throw new ParserException("Invalid cell name");
            String[] cellTokens = cellRangeName.split("(?<=[A-Za-z])(?=[0-9])|:");
            System.out.println(Arrays.toString(cellTokens));
            if (cellTokens.length != 4) throw new ParserException("Invalid cell name");
            String colLetter1 = cellTokens[0].toUpperCase();
            String colLetter2 = cellTokens[2].toUpperCase();
            rowNumber1 = Integer.valueOf(cellTokens[1]);
            rowNumber2 = Integer.valueOf(cellTokens[3]);
            colNumber1 = 0;
            for (int i = 0; i < colLetter1.length(); ++i) {
                colNumber1 = colNumber1 * 26 + (colLetter1.charAt(i) - 'A' + 1);
            }
            colNumber2 = 0;
            for (int i = 0; i < colLetter2.length(); ++i) {
                colNumber2 = colNumber2 * 26 + (colLetter2.charAt(i) - 'A' + 1);
            }
            return new CellRange(
                    tableRef,
                    Math.min(colNumber1, colNumber2),
                    Math.min(rowNumber1, rowNumber2),
                    Math.max(colNumber1, colNumber2),
                    Math.max(rowNumber1, rowNumber2)
            );
        }
    }
}
