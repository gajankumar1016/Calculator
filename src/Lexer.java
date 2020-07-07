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
        } else if (Character.isDigit(inputStream.charAt(cursor))) {
            int j = cursor + 1;
            while (j < inputStream.length() && Character.isDigit(inputStream.charAt(j))) {
                j++;
            }

            String lexeme = inputStream.substring(cursor, j);
            cursor = j;
            return new Token(TokenClass.INT, lexeme);
        } else {
            //TODO: throw an exception or return INVALID token
            System.out.println("Encountered invalid token: " + inputStream.charAt(cursor));
        }
        return null;
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
