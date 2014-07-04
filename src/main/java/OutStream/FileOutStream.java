package OutStream;

import Exceptions.StreamException;
import java.io.*;

/**
 * Created by eugenep on 02.07.14.
 */
public class FileOutStream implements OutStream {

    private FileOutputStream fileOutputStream = null;
    private File fileDestination = null;

    /**
     *
     * @param nameOfFile
     * @throws StreamException
     */
    public FileOutStream(String nameOfFile)throws StreamException
    {
        fileDestination = new File(nameOfFile);
        try {
            fileDestination .createNewFile();
            fileOutputStream = new FileOutputStream(fileDestination );
        }
        catch (IOException e) {
           throw new StreamException("file is not find");
        }
    }

    /**
     *
     * @param symbol which will write in stream
     * @throws StreamException
     */
    public void writeSymbol(int symbol)throws StreamException
    {
       try {
            fileOutputStream.write(symbol);
       }
       catch (IOException e)
        {
            throw new StreamException("write symbol error");
        }
    }

    /**
     * Close stream
     * @throws StreamException if fileOutputStream was closed early or isn't exist
     */
    public void close()throws StreamException
    {
        try {
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            throw new StreamException("close stream error");
        }
    }
}
