import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String inputStream;
    private int cursor;

    public void rewind() {
        //TODO: rewind token

    }

    public Token getNextToken() {
        if (cursor == inputStream.length()) {
            cursor++;
            return new Token(TokenClass.EOF, "$");
        } else if (cursor > inputStream.length()) {
            return null;
        }

        if (inputStream.charAt(cursor) == '+') {
            cursor++;
            return new Token(TokenClass.PLUS, "+");
        } else if (inputStream.charAt(cursor) == '-') {
            cursor++;
            return new Token(TokenClass.MINUS, "-");
        } else if (inputStream.charAt(cursor) == '*') {
            cursor++;
            return new Token(TokenClass.MULT, "*");
        } else if (inputStream.charAt(cursor) == '/') {
            cursor++;
            return new Token(TokenClass.DIV, "/");
        } else if (inputStream.charAt(cursor) == '(') {
            cursor++;
            return new Token(TokenClass.OPEN_PAREN, "(");
        } else if (inputStream.charAt(cursor) == ')') {
            cursor++;
            return new Token(TokenClass.CLOSE_PAREN, ")");
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
                return new Token(TokenClass.FLOAT, lexeme);
            } else {
                return new Token(TokenClass.INT, lexeme);
            }
        } else {
            throw new InvalidCalculatorCharacterException("Encountered invalid token: " + inputStream.charAt(cursor));
        }
    }

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
