public class Token {
    public TokenClass tokenClass;
    public String lexeme;

    public Token(TokenClass tokenClass, String lexeme) {
        this.tokenClass = tokenClass;
        this.lexeme = lexeme;
    }
}
