import java.util.ArrayList;
import java.util.List;

public class Parser {
    private enum ParseTreeNodeType { NONTERMINAL, TERMINAL }

    private enum Nonterminal { GOAL, EXPR, EXPR_PR, TERM, TERM_PR, FACTOR }

    private class ParseTreeNode {
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
    }


    private boolean parseGoal(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.GOAL);
        System.out.println("Parse Goal");

        ParseTreeNode exprNode = new ParseTreeNode(Nonterminal.EXPR);
        currParseTreeNode.children.add(exprNode);

        return parseExpr(exprNode);
    }

    private boolean parseExpr(ParseTreeNode currParseTreeNode) {
        assert (currParseTreeNode.nt == Nonterminal.EXPR);
        System.out.println("Parse Expr");
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
        System.out.println("Parse Term");
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
        System.out.println("Parse Expr'");
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
        System.out.println("Parse Term'");
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
     * 1st production of Term'
     * @param currParseTreeNode
     * @return
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
        System.out.println("Parse Factor");
        int saveIdx = currTokenIdx;
        ParseTreeNode exprNode = new ParseTreeNode(Nonterminal.EXPR);
        if (terminal(TokenClass.OPEN_PAREN)
                && parseExpr(exprNode)
                && terminal(TokenClass.CLOSE_PAREN)) {
            currParseTreeNode.children.add(exprNode);
            return true;
        }

        currTokenIdx = saveIdx;
        if (terminal(TokenClass.INT)) {
            currParseTreeNode.children.add(new ParseTreeNode(tokens.get(saveIdx)));
            return true;
        }
        return false;
    }

    private boolean terminal(TokenClass tokenType) {
        System.out.println("Terminal compare: want=" + tokenType + "; actual=" + tokens.get(currTokenIdx).tokenClass);
        return tokenType == tokens.get(currTokenIdx++).tokenClass;
    }

    private ParseTreeNode root;
    private List<Token> tokens;
    private int currTokenIdx;

    public Parser(String parseString) {
        //TODO: handle empty string
        currTokenIdx = 0;
        Lexer lexer = new Lexer(parseString);
        tokens = lexer.getAllTokens();
        System.out.println(tokens);

        root = new ParseTreeNode(Nonterminal.GOAL);

        currTokenIdx = 0;
        ParseTreeNode currParseTreeNode = this.root;
//        while ((currToken = lexer.getNextToken()) != null) {
//            System.out.println(currToken);
//        }

        boolean res  = parseGoal(currParseTreeNode);
        System.out.println(res);
        System.out.println(this.root);
    }
}
