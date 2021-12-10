package by.kosolobov.compositetask.reader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;

public class MainReader {
    private static final Logger log = LogManager.getLogger(MainReader.class);

    public MainReader() {
        //default constructor
    }

    public String read(String filePath) {
        URL fileUrl = MainReader.class
                .getClassLoader()
                .getResource(filePath);

        if (fileUrl == null) {
            log.log(Level.ERROR, "FileUrl is not correct: {}", filePath);
            return null;
        }

        File file = new File(fileUrl.getFile());
        String value = null;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();

            while (reader.ready()) {
                builder.append(reader.readLine());
                builder.append("\n");
            }
            value = builder.toString();

        } catch (FileNotFoundException e) {
            log.log(Level.ERROR, "File not found: {}", e.getMessage());
        } catch (IOException e) {
            log.log(Level.ERROR, "File is invalid: {}", e.getMessage());
        }

        return value;
    }
}
