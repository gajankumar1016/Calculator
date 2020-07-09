public class Driver {
    public static void main(String[] args) {
        String test1 = "5+";
        String test2 = "4+(7*2)";

        Number res = null;
        try {
            res = Calculator.calculate(test1);
        } catch (InvalidCalculatorExpressionException invalidCalculatorExpression) {
            invalidCalculatorExpression.printStackTrace();
        }
        System.out.println("Res: " + res);

    }
}
