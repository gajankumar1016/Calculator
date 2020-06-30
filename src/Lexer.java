public class Lexer {
    private final String inputStream;
    private int cursor;

    public void rewind() {
        //TODO: rewind token

    }

    public Token getNextToken() {
        if (cursor >= inputStream.length()) {
            return null;
        }

        if (inputStream.charAt(cursor) == '+') {
            cursor++;
            return new Token(TokenClass.PLUS, "+");
        } else if (inputStream.charAt(cursor) == '-') {
            cursor++;
            return new Token(TokenClass.SUB, "-");
        } else if (inputStream.charAt(cursor) == '*') {
            cursor++;
            return new Token(TokenClass.MULT, "*");
        } else if (inputStream.charAt(cursor) == '/') {
            cursor++;
            return new Token(TokenClass.DIV, "/");
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
        }
        return null;
    }

    public Lexer(String inputStream) {
        this.inputStream = inputStream;
        this.cursor = 0;
    }
}
