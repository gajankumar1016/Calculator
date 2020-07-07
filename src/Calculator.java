public class Calculator {
    public static int calculate(String expr) {
        Parser parser = new Parser(expr);
        ASTNode ast = parser.getAST();
        return ast.evaluate();
    }
}
