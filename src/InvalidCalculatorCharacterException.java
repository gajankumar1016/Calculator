/**
 * Thrown when the lexer encounters an unknown/invalid character.
 */
public class InvalidCalculatorCharacterException extends InvalidCalculatorExpressionException {
    public InvalidCalculatorCharacterException(String errorMessage) {
        super(errorMessage);
    }
}
