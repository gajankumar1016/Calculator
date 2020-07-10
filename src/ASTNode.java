/**
 * Represents a node in the Calculator abstract syntax tree.
 */
public class ASTNode {
    Token token;
    ASTNode left;
    ASTNode right;

    public ASTNode(Token token) {
        this.token = token;
    }

    public ASTNode(Token token, ASTNode left, ASTNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }

    /**
     * Calculates value represented by the abstract syntax tree rooted at the current ('this') node.
     *
     * @return value of the AST
     */
    public Number evaluate() {
        CalcNumber res = evaluateHelper();
        return res.getN();
    }

    /**
     * Calculates value represented by the AST rooted at the current ('this') node. Returns result in a custom number
     * type that handles differences in arithmetic operation implementation based on whether the argument is an integer
     * or floating-point number.
     *
     * @return value of AST in custom CalcNumber format
     */
    private CalcNumber evaluateHelper() {
        if (token.tokenClass == TokenClass.PLUS) {
            return left.evaluateHelper().add(right.evaluateHelper());
        } else if (token.tokenClass == TokenClass.MINUS) {
            return left.evaluateHelper().subtract(right.evaluateHelper());
        } else if (token.tokenClass == TokenClass.MULT) {
            return left.evaluateHelper().multiply(right.evaluateHelper());
        } else if (token.tokenClass == TokenClass.DIV) {
            return left.evaluateHelper().divide(right.evaluateHelper());
        } else if (token.tokenClass == TokenClass.INT) {
            return new CalcNumber(Integer.parseInt(token.lexeme));
        } else if (token.tokenClass == TokenClass.FLOAT) {
            return new CalcNumber(Float.parseFloat(token.lexeme));
        }
        throw new IllegalStateException("AST in invalid state. Discovered during AST evaluation.");
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "token=" + token +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
