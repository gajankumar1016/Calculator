import java.util.Objects;

/**
 * Class representing a token in the calculator language. Includes the lexeme and its token class.
 */
public class Token {
    public TokenClass tokenClass;
    public String lexeme;

    public Token(TokenClass tokenClass, String lexeme) {
        this.tokenClass = tokenClass;
        this.lexeme = lexeme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return tokenClass == token.tokenClass &&
                Objects.equals(lexeme, token.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenClass, lexeme);
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenClass=" + tokenClass +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
