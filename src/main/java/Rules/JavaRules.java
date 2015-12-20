package Rules;

/**
 * Created by eugenep on 19.12.15.
 */
public class JavaRules implements CodeRules{
    private int indent = ' ';
    private int indentCounter = 2;
    private int transferLineSymbol = '\n';
    private int openParenthesisSymbol = '{';
    private int closeParenthesisSymbol = '}';

    public int getIndentSymbol() {return indent;}

    public int getIndentCounter() {return  indentCounter;}

    public int getTransferLineSymbol() {return transferLineSymbol;}

    public int getOpenParenthesisSymbol() {return openParenthesisSymbol;}

    public int getCloseParenthesisSymbol() {return closeParenthesisSymbol;}
}
