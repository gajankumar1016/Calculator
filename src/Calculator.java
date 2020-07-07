public class Calculator {

    public static Number calculate(String expr) {
        int errorChar = -1;
        Parser parser = new Parser(expr);
        ASTNode ast = parser.getAST();
        System.out.println(ast);
        return ast.evaluate();
    }
}
