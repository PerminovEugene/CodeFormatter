package Rules;

/**
 * Created by eugenep on 19.12.15.
 */
public class JavaRules implements CodeRules{
    int indent = ' ';
    int indentCounter = 2;

    public int getIndentSymbol() {return indent;}

    public int getIndentCounter() {return  indentCounter;}
}
