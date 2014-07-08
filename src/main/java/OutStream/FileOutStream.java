package OutStream;

import Exceptions.StreamException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by eugenep on 02.07.14.
 */
public class FileOutStream implements OutStream {
    private static Logger logger = Logger.getLogger(FileOutStream.class);

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
        try
        {
            fileDestination .createNewFile();
            fileOutputStream = new FileOutputStream(fileDestination );
        }
        catch (IOException e)
        {
            logger.error("Stream exception in create FileOutStream. ");
            throw new StreamException("File is not found. ");
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
            logger.error("Stream exception at write symbol in FileOutStream. ");
            throw new StreamException("Write symbol error. ");
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
            logger.error("Stream exception in close FileOutStream. ");
            throw new StreamException("Close stream error");
        }
    }
}
