import static org.junit.jupiter.api.Assertions.*;

class ASTNodeTest {

    @org.junit.jupiter.api.Test
    void testSingleNodePlus() {
        ASTNode root = new ASTNode(new Token(TokenClass.PLUS, "+"));
        root.left = new ASTNode(new Token(TokenClass.INT, "5"));
        root.right = new ASTNode(new Token(TokenClass.INT, "6"));
        assertEquals(root.evaluate(), 11);
    }

    @org.junit.jupiter.api.Test
    void twoLevels() {
        // 5 + 4*5
        ASTNode root = new ASTNode(new Token(TokenClass.PLUS, "+"));
        root.left = new ASTNode(new Token(TokenClass.INT, "5"));
        root.right = new ASTNode(new Token(TokenClass.MULT, "*"),
                new ASTNode(new Token(TokenClass.INT, "4")),
                new ASTNode(new Token(TokenClass.INT, "5")));

        assertEquals(root.evaluate(), 25);
    }
}