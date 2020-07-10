import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a lexer for the Calculator language. Breaks up expression into logical tokens in the language.
 */
public class Lexer {
    private final String inputStream;
    private int cursor;
    private Token lastToken = null;


    /**
     * Gets the next token from the input string.
     *
     * @return the next token
     */
    public Token getNextToken() {
        if (cursor == inputStream.length()) {
            cursor++;
            lastToken = new Token(TokenClass.EOF, "$");
            return lastToken;
        } else if (cursor > inputStream.length()) {
            return null;
        }

        if (inputStream.charAt(cursor) == '+') {
            cursor++;
            lastToken = new Token(TokenClass.PLUS, "+");
            return lastToken;
        } else if (inputStream.charAt(cursor) == '-') {
            Token zeroToken = new Token(TokenClass.INT, "0");
            Token openParenToken = new Token(TokenClass.OPEN_PAREN, "(");
            boolean negativeLeadsExpression = cursor == 0;
            boolean effectivelyInsertedZero = zeroToken.equals(lastToken);
            boolean negativeStartsExpr = openParenToken.equals(lastToken);
            if ((negativeLeadsExpression || negativeStartsExpr) && !effectivelyInsertedZero) {
                //Don't increment cursor, as next call should return us to handling the current negative sign
                lastToken = zeroToken;
                return zeroToken;
            }

            cursor++;
            lastToken = new Token(TokenClass.MINUS, "-");
            return lastToken;
        } else if (inputStream.charAt(cursor) == '*') {
            cursor++;
            lastToken = new Token(TokenClass.MULT, "*");
            return lastToken;
        } else if (inputStream.charAt(cursor) == '/') {
            cursor++;
            lastToken = new Token(TokenClass.DIV, "/");
            return lastToken;
        } else if (inputStream.charAt(cursor) == '(') {
            cursor++;
            lastToken = new Token(TokenClass.OPEN_PAREN, "(");
            return lastToken;
        } else if (inputStream.charAt(cursor) == ')') {
            cursor++;
            lastToken = new Token(TokenClass.CLOSE_PAREN, ")");
            return lastToken;
        } else if (inputStream.charAt(cursor) == ' ') {
            while (cursor < inputStream.length() && inputStream.charAt(cursor) == ' ') {
                cursor++;
            }
            return getNextToken();
        } else if (Character.isDigit(inputStream.charAt(cursor)) || inputStream.charAt(cursor) == '.') {
            int numDecimals = 0;
            int j = cursor;
            while (j < inputStream.length() &&
                    (Character.isDigit(inputStream.charAt(j)) || inputStream.charAt(j) == '.')) {
                if (inputStream.charAt(j) == '.') {
                    numDecimals++;
                    if (numDecimals > 1) {
                        throw new InvalidCalculatorExpressionException("Too many decimal points in float.\n");
                    }
                }
                j++;
            }

            String lexeme = inputStream.substring(cursor, j);
            cursor = j;
            if (numDecimals > 0) {
                lastToken = new Token(TokenClass.FLOAT, lexeme);
            } else {
                lastToken = new Token(TokenClass.INT, lexeme);
            }
            return lastToken;
        } else {
            throw new InvalidCalculatorCharacterException("Encountered invalid token: " + inputStream.charAt(cursor));
        }
    }

    /**
     * Tokenizes the whole input stream in one go.
     *
     * @return list of all the tokens from the input string.
     */
    public List<Token> getAllTokens() {
        List<Token> tokenList = new ArrayList<>();
        Token currToken;

        while ((currToken = getNextToken()) != null) {
            tokenList.add(currToken);
        }

        return tokenList;
    }

    public Lexer(String inputStream) {
        this.inputStream = inputStream;
        this.cursor = 0;
    }
}
