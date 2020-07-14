/**
 * Glue class that hooks together the necessary utilities to parse and evaluate an arithmetic expression input string.
 */
public class Calculator {

    //Suppress default constructor to make the Calculator class noninstantiable
    private Calculator() {
        throw new AssertionError("Cannot instantiate a calculator.");
    }

    /**
     * Attempts to evaluate an arithmetic expression given as a String
     *
     * @param expr string representation of arithmetic expression
     * @return the value of the input arithmetic expression
     */
    public static Number calculate(String expr) {
        Parser parser = new Parser(expr);
        ASTNode ast = parser.getAST();
        System.out.println("Abstract syntax tree: " + ast);
        return ast.evaluate();
    }
}
