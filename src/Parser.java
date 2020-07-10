import java.util.ArrayList;
import java.util.List;

public class Parser {
    private enum ParseTreeNodeType {NONTERMINAL, TERMINAL}

    private enum Nonterminal {GOAL, EXPR, EXPR_PR, TERM, TERM_PR, FACTOR}

    private final ParseTreeNode root;
    private final List<Token> tokens;
    private int currTokenIdx;

    public Parser(String parseString) {
        Lexer lexer = new Lexer(parseString);
        tokens = lexer.getAllTokens();
        System.out.println("Lexing result: " + tokens);

        root = new ParseTreeNode(Nonterminal.GOAL);
        currTokenIdx = 0;

        boolean isParseSuccessful = parseGoal(this.root);
        if (!(isParseSuccessful && tokens.get(currTokenIdx).tokenClass == TokenClass.EOF)) {
            throw new InvalidCalculatorExpressionException(": '" + parseString + "'");
        }

        System.out.println("Parse tree: " + this.root);
    }

    private static class ParseTreeNode {
        ParseTreeNodeType nodeType;
        Nonterminal nt;
        Token token;
        List<ParseTreeNode> children;

        public ParseTreeNode(ParseTreeNodeType nodeType) {
            this(nodeType, null, null);
        }

        public ParseTreeNode(Nonterminal nt) {
            this(ParseTreeNodeType.NONTERMINAL, nt, null);
        }

        public ParseTreeNode(Token token) {
            this(ParseTreeNodeType.TERMINAL, null, token);
        }

        private ParseTreeNode(ParseTreeNodeType nodeType, Nonterminal nt, Token token) {
            this.nodeType = nodeType;
            this.nt = nt;
            this.token = token;
            this.children = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "ParseTreeNode{" +
                    "nodeType=" + nodeType +
                    ", nt=" + nt +
                    ", token=" + token +
                    ", children=" + children +
                    '}';
        }
    }

    /***************************************************************************************
     *
     *  RECURSIVE DESCENT PARSING FUNCTIONS
     *
     ***************************************************************************************/

    private boolean parseGoal(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.GOAL);

        ParseTreeNode exprNode = new ParseTreeNode(Nonterminal.EXPR);
        currParseTreeNode.children.add(exprNode);

        return parseExpr(exprNode);
    }

    private boolean parseExpr(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.EXPR);
        ParseTreeNode termNode = new ParseTreeNode(Nonterminal.TERM);
        ParseTreeNode exprPrNode = new ParseTreeNode(Nonterminal.EXPR_PR);
        boolean res = parseTerm(termNode) && parseExprPr(exprPrNode);
        if (res) {
            currParseTreeNode.children.add(termNode);
            currParseTreeNode.children.add(exprPrNode);
        }

        return res;
    }

    private boolean parseTerm(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.TERM);
        ParseTreeNode factorNode = new ParseTreeNode(Nonterminal.FACTOR);
        ParseTreeNode termPrNode = new ParseTreeNode(Nonterminal.TERM_PR);
        boolean res = parseFactor(factorNode) && parseTermPr(termPrNode);
        if (res) {
            currParseTreeNode.children.add(factorNode);
            currParseTreeNode.children.add(termPrNode);
        }
        return res;
    }


    private boolean parseExprPr(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.EXPR_PR);
        int saveIdx = currTokenIdx;
        boolean res = parseExpr1(currParseTreeNode);
        if (res) {
            return true;
        }
        currTokenIdx = saveIdx;
        res = parseExpr2(currParseTreeNode);
        if (res) {
            return true;
        }
        currTokenIdx = saveIdx;
        res = parseExpr3(currParseTreeNode);
        if (res) {
            return true;
        }
        return false;
    }

    private boolean parseExpr1(ParseTreeNode currParseTreeNode) {
        int saveIdx = currTokenIdx;
        ParseTreeNode termNode = new ParseTreeNode(Nonterminal.TERM);
        ParseTreeNode exprPrNode = new ParseTreeNode(Nonterminal.EXPR_PR);
        if (terminal(TokenClass.PLUS) && parseTerm(termNode) && parseExprPr(exprPrNode)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            currParseTreeNode.children.add(termNode);
            currParseTreeNode.children.add(exprPrNode);
            return true;
        }

        return false;
    }

    private boolean parseExpr2(ParseTreeNode currParseTreeNode) {
        int saveIdx = currTokenIdx;
        ParseTreeNode termNode = new ParseTreeNode(Nonterminal.TERM);
        ParseTreeNode exprPrNode = new ParseTreeNode(Nonterminal.EXPR_PR);
        if (terminal(TokenClass.MINUS) && parseTerm(termNode) && parseExprPr(exprPrNode)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            currParseTreeNode.children.add(termNode);
            currParseTreeNode.children.add(exprPrNode);
            return true;
        }

        return false;
    }

    private boolean parseExpr3(ParseTreeNode currParseTreeNode) {
        return true;
    }

    private boolean parseTermPr(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.TERM_PR);
        int saveIdx = currTokenIdx;
        boolean res = parseTermPr1(currParseTreeNode);
        if (res) {
            return true;
        }
        currTokenIdx = saveIdx;
        res = parseTermPr2(currParseTreeNode);
        if (res) {
            return true;
        }
        currTokenIdx = saveIdx;
        res = parseTermPr3(currParseTreeNode);
        if (res) {
            return true;
        }
        return false;
    }

    /**
     * Tries to match input using 1st production of Term'
     *
     * @param currParseTreeNode a Term' parse tree node; will add children to this node if the production is a match
     * @return true if there's a match, false otherwise
     */
    private boolean parseTermPr1(ParseTreeNode currParseTreeNode) {
        int saveIdx = currTokenIdx;
        ParseTreeNode factorNode = new ParseTreeNode(Nonterminal.FACTOR);
        ParseTreeNode termPrNode = new ParseTreeNode(Nonterminal.TERM_PR);
        if (terminal(TokenClass.MULT) && parseFactor(factorNode) && parseTermPr(termPrNode)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            currParseTreeNode.children.add(factorNode);
            currParseTreeNode.children.add(termPrNode);
            return true;
        }

        return false;
    }

    private boolean parseTermPr2(ParseTreeNode currParseTreeNode) {
        int saveIdx = currTokenIdx;
        ParseTreeNode factorNode = new ParseTreeNode(Nonterminal.FACTOR);
        ParseTreeNode termPrNode = new ParseTreeNode(Nonterminal.TERM_PR);
        if (terminal(TokenClass.DIV) && parseFactor(factorNode) && parseTermPr(termPrNode)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            currParseTreeNode.children.add(factorNode);
            currParseTreeNode.children.add(termPrNode);
            return true;
        }

        return false;
    }

    private boolean parseTermPr3(ParseTreeNode currParseTreeNode) {
        //match emptpy string
        return true;
    }

    private boolean parseFactor(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.FACTOR);
        int saveIdx = currTokenIdx;
        ParseTreeNode exprNode = new ParseTreeNode(Nonterminal.EXPR);
        if (terminal(TokenClass.OPEN_PAREN)
                && parseExpr(exprNode)
                && terminal(TokenClass.CLOSE_PAREN)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            currParseTreeNode.children.add(exprNode);
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(currTokenIdx - 1)));
            return true;
        }

        currTokenIdx = saveIdx;
        if (terminal(TokenClass.INT)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            return true;
        }

        currTokenIdx = saveIdx;
        if (terminal(TokenClass.FLOAT)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            return true;
        }

        return false;
    }

    private boolean terminal(TokenClass tokenType) {
        return tokenType == tokens.get(currTokenIdx++).tokenClass;
    }


    /* *************************************************************************
     *
     * PARSE TREE TO ABSTRACT SYNTAX TREE CONVERSION FUNCTIONS
     *
     ***************************************************************************/

    /**
     * Gets abstract syntax tree from the already constructed parse tree.
     *
     * @return root of the abstract syntax tree
     */
    public ASTNode getAST() {
        return getASTHelper(root);
    }

    private ASTNode getASTExpr(ParseTreeNode currParseTreeNode) {
        ParseTreeNode termChild = currParseTreeNode.children.get(0);
        ParseTreeNode exprPrChild = currParseTreeNode.children.get(1);

        ASTNode termChildAST = getASTHelper(termChild);
        ASTNode exprPrChildAST = getASTHelper(exprPrChild);

        if (exprPrChildAST == null) {
            return termChildAST;
        } else {
            exprPrChildAST.left = termChildAST;
            return exprPrChildAST;
        }
    }

    private ASTNode getASTExprPr(ParseTreeNode currParseTreeNode) {
        if (currParseTreeNode.children.isEmpty()) {
            return null;
        } else {
            ParseTreeNode plusOrMinusChild = currParseTreeNode.children.get(0);
            ParseTreeNode termChild = currParseTreeNode.children.get(1);
            ParseTreeNode exprPrChild = currParseTreeNode.children.get(2);

            ASTNode termChildAST = getASTHelper(termChild);
            ASTNode exprPrChildAST = getASTHelper(exprPrChild);

            ASTNode currAST = new ASTNode(plusOrMinusChild.token);
            if (exprPrChildAST == null) {
                currAST.right = termChildAST;
            } else {
                exprPrChildAST.left = termChildAST;
                currAST.right = exprPrChildAST;
            }
            return currAST;
        }
    }

    private ASTNode getASTTerm(ParseTreeNode currParseTreeNode) {
        ParseTreeNode factorChild = currParseTreeNode.children.get(0);
        ParseTreeNode termPrChild = currParseTreeNode.children.get(1);
        ASTNode factorChildAST = getASTHelper(factorChild);
        ASTNode termPrChildAST = getASTHelper(termPrChild);
        if (termPrChildAST == null) {
            return factorChildAST;
        } else {
            termPrChildAST.left = factorChildAST;
            return termPrChildAST;
        }
    }

    private ASTNode getASTTermPr(ParseTreeNode currParseTreeNode) {
        if (currParseTreeNode.children.isEmpty()) {
            return null;
        }
        ParseTreeNode timesOrDivChild = currParseTreeNode.children.get(0);
        ParseTreeNode factorChild = currParseTreeNode.children.get(1);
        ParseTreeNode termPrChild = currParseTreeNode.children.get(2);

        ASTNode factorChildAST = getASTHelper(factorChild);
        ASTNode termPrChildAST = getASTHelper(termPrChild);

        ASTNode currNode = new ASTNode(timesOrDivChild.token);
        if (termPrChildAST == null) {
            currNode.right = factorChildAST;
        } else {
            termPrChildAST.left = factorChildAST;
            currNode.right = termPrChildAST;
        }

        return currNode;
    }

    private ASTNode getASTFactor(ParseTreeNode currParseTreeNode) {
        int numChildren = currParseTreeNode.children.size();
        assert (numChildren == 1 || numChildren == 3);
        if (numChildren == 1) {
            ParseTreeNode numChild = currParseTreeNode.children.get(0);
            return new ASTNode(numChild.token);
        } else {
            ParseTreeNode exprChild = currParseTreeNode.children.get(1);
            return getASTHelper(exprChild);
        }
    }

    private ASTNode getASTHelper(ParseTreeNode currParseTreeNode) {

        switch (currParseTreeNode.nt) {

            case GOAL -> {
                return getASTHelper(currParseTreeNode.children.get(0));
            }
            case EXPR -> {
                return getASTExpr(currParseTreeNode);
            }
            case EXPR_PR -> {
                return getASTExprPr(currParseTreeNode);
            }
            case TERM -> {
                return getASTTerm(currParseTreeNode);
            }
            case TERM_PR -> {
                return getASTTermPr(currParseTreeNode);
            }
            case FACTOR -> {
                return getASTFactor(currParseTreeNode);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "root=" + root +
                '}';
    }
}
