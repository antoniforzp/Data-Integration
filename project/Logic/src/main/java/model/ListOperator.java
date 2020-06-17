package model;

import java.io.*;

public class ListOperator {

    private static final String fileName = "files/list/list.txt";

    static public void addTitle(String title) throws IOException {

        StringBuilder strB = new StringBuilder();
        strB.append(title).append("\n");

        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            strB.append(st).append("\n");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(strB.toString());
        writer.close();
    }

    static public void deleteTitle(int line) throws IOException {
        StringBuilder strB = new StringBuilder();

        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        int count = 0;
        String st;
        while ((st = br.readLine()) != null) {

            if (count != line) {
                strB.append(st).append("\n");
            }
            count++;
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(strB.toString());
        writer.close();
    }
}
