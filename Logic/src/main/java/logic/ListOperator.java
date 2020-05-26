package logic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ListOperator {

    static public void addTitle(String title) throws IOException {

        StringBuilder strB = new StringBuilder();
        File file = new File("list.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            strB.append(st).append("\n");
        }

        
    }

    static public void deleteTitle(int line) throws IOException {
        File inputFile = new File("list.txt");
        File tempFile = new File("list_temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        int count = 0;

        while ((currentLine = reader.readLine()) != null) {
            count++;
            if (count == line) {
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
}
