package model;

import java.io.File;
import java.util.regex.Pattern;

public class FileChecker {

    public static boolean checkFile(String filename) {
        if (!Pattern.matches(".*\\.xml", filename)) {
            return false;
        } else {
            new File(filename);
            return true;
        }
    }

}
