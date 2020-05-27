package model.logic;

import model.LoadingTask;

public class GenerateLogic {

    private static LoadingTask task;

    public static void generate(String filename) {
        task = new LoadingTask(filename);
        new Thread(task).start();
    }

    public static boolean cancel(){
        if(task != null){
            task.cancel();
            return true;
        }
        return false;
    }


    public static LoadingTask getTask() {
        return task;
    }
}
