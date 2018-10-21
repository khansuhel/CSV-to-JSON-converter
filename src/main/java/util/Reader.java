package util;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements AutoCloseable
{
    private CSVReader csvReader;


    public Reader()
    {
        try
        {
            this.csvReader = new CSVReader(new FileReader(ClassLoader.getSystemClassLoader().getResource("test.csv").getFile()));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    public String[] readNext() throws IOException
    {
        String[] line = csvReader.readNext();
        return line;
    }


    public void close() throws Exception
    {
        this.csvReader.close();
    }
}
