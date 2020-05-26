package gui.tabs;

import gui.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

import java.io.*;

public class GenerateController {

    public TextArea textArea;
    public ProgressBar progressBar;

    @FXML
    void initialize() {

    }

    private void readFile() {
        StringBuilder strB = new StringBuilder();
        File file = new File("movies.xml");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                strB.append(line).append("\n");
            }
        } catch (IOException e) {
            PrimaryController.displayAlert(e.getMessage());
            e.printStackTrace();
        }
        textArea.setText(strB.toString());
    }

    @FXML
    void generate() {


        //generate logic function

//        progressBar.setProgress(//value);
//        readFile();

    }
}
