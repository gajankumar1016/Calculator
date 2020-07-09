public class Calculator {

    public static Number calculate(String expr) {
        int errorChar = -1;
        Parser parser = new Parser(expr);
        ASTNode ast = parser.getAST();
        System.out.println("Abstract syntax tree: " + ast);
        return ast.evaluate();
    }
}
