package Context;

/**
 * Created by eugenep on 18.10.15.
 */
public class Context {
    private int levelOfNesting;
    private boolean isNewString;
    private boolean pastIsOperand;
    private int pastSymbol;
    private int symbol ;

    public Context() {
      levelOfNesting = 0;
      isNewString = false;
      pastIsOperand = false;
      pastSymbol = 0;
      symbol = 0;
    }

    public int getLevelOfNesting () {
        return this.levelOfNesting;
    }
    public void upLevelOfNesting() {
        this.levelOfNesting++;
    }
    public void downLevelOfNesting() {
        this.levelOfNesting--;
    }
    public void toZeroLevelOfNesting() {
        this.levelOfNesting = 0;
    }

    public boolean getIsNewString () {
        return this.isNewString;
    }
    public void setIsNewString(boolean newStateString) {
        this.isNewString = newStateString;
    }

    public boolean getPastIsOperand () {
        return this.pastIsOperand;
    }
    public void setPastIsOperand(boolean newStatePastIsOperand) {
        this.pastIsOperand = newStatePastIsOperand;
    }

    public int getPastSymbol () {
        return this.pastSymbol;
    }
//    public void setPastSymbol(int newSymbol) {
//        this.pastSymbol = newSymbol;
//    }

    public int getSymbol () {
        return this.symbol;
    }
    public void setSymbol(int newSymbol) {
        this.pastSymbol = this.symbol;
        this.symbol = newSymbol;
    }
}
