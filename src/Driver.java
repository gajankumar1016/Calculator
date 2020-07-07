public class Driver {
    public static void main(String[] args) {
        String test1 = "5+6";
        String test2 = "4+(7*2)";

        int res = Calculator.calculate(test1);
        System.out.println("Res: " + res);

    }
}
