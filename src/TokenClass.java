/**
 * Enum to represent the class of a token in our calculator language. Used to label each lexeme returned by the Lexer.
 */
public enum TokenClass {
    PLUS,
    MINUS,
    MULT,
    DIV,
    INT,
    FLOAT,
    OPEN_PAREN,
    CLOSE_PAREN,
    EOF,
}
