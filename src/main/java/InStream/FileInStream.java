package InStream;

import Exceptions.StreamException;

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
            throw new StreamException("file not exist");
        }
        try {
            fileInputStream = new java.io.FileInputStream(fileSource);
        }
        catch (FileNotFoundException e)
        {
            throw new StreamException("file not found");
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
        catch (IOException e)
        {
            throw new StreamException("read symbol error");
        }
        return data;
    }

    /**
     * test for take info about file
     */
    public void testingFileSource()
    {
        if (!fileSource.exists())
        {
            System.out.println("file not exist");
        }
        else {
            try {
                System.out.println(fileSource.getCanonicalPath());
                System.out.println(" full file name;");
                System.out.println(fileSource.getName());
                System.out.println(" name of file;");
            }
            catch (IOException e)
            {

            }
            if (fileSource.canRead())
            {
                System.out.println("file not for read;");
            }
            if (fileSource.canWrite())
            {
                System.out.println("file not for write");
            }
        }
    }

    /**
     * Close stream
     * @throws StreamException if file was stream was closed early or isn't exist
     */
    public void close() throws StreamException
    {
        try {
            fileInputStream.close();
        }
        catch (IOException e)
        {
            throw new StreamException("close stream error");
        }
    }

    /**
     *
     * @return true if fileInputStream isn't finished
     * @throws StreamException if fileInputStream isn't exist
     */
    public boolean isEnd() throws StreamException
    {
        try {
            return (fileInputStream.available() <= 0);
        }
        catch (IOException s)
            {
                throw new StreamException("end of file error");
            }
    }
}
