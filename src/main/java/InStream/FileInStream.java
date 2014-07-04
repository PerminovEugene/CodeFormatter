package InStream;

import Exceptions.StreamException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by eugenep on 01.07.14.
 */
public class FileInStream implements InStream {
    private java.io.FileInputStream fileInputStream;
    private File fileSource;

    /**
     * Create new FileInStream
     * @param fileName name of file for open
     * @throws StreamException if fileName not exist or not found
     */
    public FileInStream(String fileName)throws StreamException
    {
        fileSource = new File(fileName);
        if (!fileSource.exists())
        {
            Logger.getLogger("Stream exception file not exist in FileInSteam");
            throw new StreamException("File not exist");
        }
        try {
            fileInputStream = new java.io.FileInputStream(fileSource);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            Logger.getLogger("Stream exception in create FileInStream. ");
            throw new StreamException("File not found");
        }
    }

    /**
     * Read one symbol from stream
     * @return  symbol which read
     * @throws StreamException if read from end of file
     */
    public int readSymbol() throws StreamException
    {
        int data = 0;
        try {
            data = fileInputStream.read();
        }
        catch (IOException exception)
        {
            Logger.getLogger("Stream exception at read in FileOutStream. ");
            throw new StreamException("Read symbol error");
        }
        return data;
    }

    /**
     * Close stream.
     * @throws StreamException if file was stream was closed early or isn't exist
     */
    public void close() throws StreamException
    {
        try {
            fileInputStream.close();
        }
        catch (IOException e)
        {
            Logger.getLogger("Stream exception in close FileInStream. ");
            throw new StreamException("Close stream error.");
        }
    }

    /**
     * For knowledge about end of stream.
     * @return true if fileInputStream isn't finished
     * @throws StreamException if fileInputStream isn't exist
     */
    public boolean isEnd() throws StreamException
    {
        try {
            return (fileInputStream.available() <= 0);
        }
        catch (IOException exception)
            {
                Logger.getLogger("Stream exception at isEnd in FileInStream. ");
                throw new StreamException("End of file error");
            }
    }
}
