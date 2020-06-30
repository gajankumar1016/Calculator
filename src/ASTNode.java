import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ASTNode {

    private static final HashMap<TokenClass, BinOp> operatorMap = new HashMap<>();

    static {
        operatorMap.put(TokenClass.PLUS, new BinOp() {
            @Override
            public int evaluate(int arg1, int arg2) {
                return arg1 + arg2;
            }
        });
        operatorMap.put(TokenClass.SUB, new BinOp() {
            @Override
            public int evaluate(int arg1, int arg2) {
                return arg1 - arg2;
            }
        });
        operatorMap.put(TokenClass.MULT, new BinOp() {
            @Override
            public int evaluate(int arg1, int arg2) {
                return arg1 * arg2;
            }
        });
        operatorMap.put(TokenClass.DIV, new BinOp() {
            @Override
            public int evaluate(int arg1, int arg2) {
                return arg1 / arg2;
            }
        });
    }

    private static final Set<TokenClass> binops = new HashSet<>();
    static {
        binops.add(TokenClass.PLUS);
        binops.add(TokenClass.SUB);
        binops.add(TokenClass.MULT);
        binops.add(TokenClass.DIV);
    }


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

    public int evaluate() {
        if (binops.contains(token.tokenClass)) {
            BinOp binop =  operatorMap.get(token.tokenClass);
            return binop.evaluate(left.evaluate(), right.evaluate());
        } else if (token.tokenClass == TokenClass.INT) {
            return Integer.parseInt(token.lexeme);
        }
        return 0;
    }
}
