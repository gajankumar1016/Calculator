import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter an arithmetic expression or type 'q' to quit: \n>>> ");
            String userInput = scanner.nextLine();
            if (userInput.equals("q")) {
                break;
            }
            Number res = null;
            try {
                res = Calculator.calculate(userInput);
                System.out.println(">>> " + res + "\n");
            } catch (InvalidCalculatorExpressionException invalidCalculatorExpression) {
                System.out.println("ERROR: Invalid expression. Could not parse.\n");
            }

        }
        scanner.close();
    }
}
