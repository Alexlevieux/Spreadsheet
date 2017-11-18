package function;

public enum Operator implements Token{
    ADD(2, true, 2, 0), SUB(2, true, 2, 0), MUL(3, true, 2, 0),
    DIV(3, true, 2, 0), EXP(4, false, 2, 0), PERCENT(5, false, 1, 0),
    EQ(0, true, 2, 2), NEQ(0, true, 2, 2), GT(0, true, 2, 2),
    LT(0, true, 2, 2), GE(0, true, 2, 2), LE(0, true, 2, 2),
    CONCAT(1, true, 2, 1);
    private int precedence, args, returnType;
    private boolean leftAssoc;

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssoc() {
        return leftAssoc;
    }

    public int getArgs() {
        return args;
    }

    public int getReturnType() {
        return returnType;
    }

    /**
     *
     * @param precedence higher value = higher precedence
     * @param leftAssoc left association: 0, right association: 1
     * @param args number of arguments needed for operator, e.g. + needs both left and right operand (2),
     *             % needs only a left operand (1).
     * @param returnType return type of operator
     *                   <table>
     *                   <tr>
     *                      <td>0</td><td>Number</td>
     *                   </tr>
     *                   <tr>
     *                      <td>1</td><td>String</td>
     *                   </tr>
     *                   <tr>
     *                      <td>2</td><td>Boolean</td>
     *                   </tr>
     *                   </table>
     */
    Operator(int precedence, boolean leftAssoc, int args, int returnType) {
        this.precedence = precedence;
        this.leftAssoc = leftAssoc;
        this.args = args;
        this.returnType = returnType;
    }
}
