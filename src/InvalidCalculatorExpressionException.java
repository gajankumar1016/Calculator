/**
 * Thrown when parsing is impossible due to invalid structure of the input.
 */
public class InvalidCalculatorExpressionException extends RuntimeException {
    public InvalidCalculatorExpressionException(String errorMessage) {
        super(errorMessage);
    }
}
