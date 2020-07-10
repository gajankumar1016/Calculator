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

    public Number evaluate() {
        CalcNumber res = evaluateHelper();
        return res.getN();
    }

    public CalcNumber evaluateHelper() {
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
