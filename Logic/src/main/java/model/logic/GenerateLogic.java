package model.logic;

import model.LoadingTask;

import java.io.File;
import java.util.regex.Pattern;

public class GenerateLogic {

    private static LoadingTask task;

    public static boolean generate(String filename) {

        if(checkFile(filename)){
            task = new LoadingTask(filename);
            new Thread(task).start();
            return true;
        }
        return false;
    }

    public static boolean cancel() {
        if (task != null && task.isRunning()) {
            task.cancel();
            return true;
        }
        return false;
    }

    public static LoadingTask getTask() {
        return task;
    }

    private static boolean checkFile(String filename) {
        if (!Pattern.matches(".*\\.xml", filename)) {
            return false;
        } else {
            new File(filename);
            return true;
        }
    }
}
