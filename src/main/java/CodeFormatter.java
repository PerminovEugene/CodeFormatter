import Exceptions.FormatterException;
import Exceptions.StreamException;
import InStream.InStream;
import OutStream.OutStream;

/**
 * Created by eugenep on 01.07.14.
 * Class for static code refactor.
 */


public class CodeFormatter {

    private int levelOfNesting = 0;
    private int spaceCounter;

    private void increaseNesting()
    {
        levelOfNesting++;
    }
    private void reduceNesting()throws FormatterException {
        levelOfNesting--;
        if(levelOfNesting < 0)
        {
            throw new FormatterException("} mor then {");
        }
    }

    /**
     * Formatted source stream. Removed extra spaces. Did one
     * @param source stream with source symbols
     * @param destination stream for result
     *
     */
    public void format(InStream source,OutStream destination)
    {
        if (source == null)
        {
            System.out.println("InStream is null");
            return;
        }
        if (destination == null)
        {
            System.out.println("OutStream is null");
            return;
        }
        spaceCounter = 4;
        int symbol;
        boolean isNewString = false;
        boolean isAloneSpaceButton = false;
        boolean isAlonePoint = false;
        try{
            while (!source.isEnd()) {
                symbol = source.readSymbol();
                switch (symbol) {
                    case '{': {
                        destination.writeSymbol(symbol);
                        isNewString = true;
                        increaseNesting();
                        goNextString(destination);
                        break;

                    }
                    case '}': {
                        destination.writeSymbol(symbol);
                        isNewString = true;
                        reduceNesting();
                        goNextString(destination);
                        break;
                    }
                    case ';': {
                        destination.writeSymbol(symbol);
                        isNewString = true;
                        goNextString(destination);
                        break;
                    }
                    case ' ': {
                        if (!isNewString) {
                            if (isAloneSpaceButton) {
                                isAloneSpaceButton = false;
                                destination.writeSymbol(symbol);
                            }
                        }
                        break;
                    }
                    case ',': {
                        if (!isNewString) {
                            if (isAlonePoint) {
                                isAlonePoint = false;
                                destination.writeSymbol(symbol);

                            }
                        }
                        break;
                    }
                    case '\n': {
                        break;
                    }
                    default: {
                        isNewString = false;
                        isAloneSpaceButton = true;
                        destination.writeSymbol(symbol);
                        break;
                    }
                }
            }
        }
        catch (StreamException streamException)
        {
            System.out.println("Stream exception:");
            System.out.println(streamException.problem);
        }
        catch (FormatterException formatterException)
        {
            System.out.println("formatter exception:");
            System.out.println(formatterException.problem);
        }

    }
    private void goNextString(OutStream destination)
    {
        //go to next string
        int symbol = '\n';
        try {
            destination.writeSymbol(symbol);
            symbol = ' ';
            for (int i = 0; i < spaceCounter * levelOfNesting; i++) {
                destination.writeSymbol(symbol);
            }
        }
        catch (StreamException e)
        {
            System.out.println("Stream exception");
        }

    }

}
