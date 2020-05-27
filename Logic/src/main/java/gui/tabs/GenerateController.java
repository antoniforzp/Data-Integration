package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Fetch;
import model.LoadingTask;

import java.io.*;
import java.util.List;

public class GenerateController {

    public TextArea listOfMovies;
    public TextArea generatedXml;
    public TextArea feedback;

    public TextField textField;

    public ProgressBar progressBar;

    public ImageView feedbackIcon;

    @FXML
    void initialize() {
        setListOfMovies();

        feedback.setEditable(false);

        String filename = "movies.xml";
        textField.setText(filename);
    }

    private void readFile() {
        feedback.setText("");

        StringBuilder strB = new StringBuilder();
        File file = new File(textField.getText());
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            setCorrect();

            String line;
            while ((line = br.readLine()) != null) {
                strB.append(line).append("\n");
            }
        } catch (IOException e) {
            setWrong();
            feedback.setText(e.getMessage());
            e.printStackTrace();
        }
        generatedXml.setText(strB.toString());
    }

    private void setListOfMovies(){
        List<String> movies = Fetch.getAllTitles();
        StringBuilder strB = new StringBuilder();

        if (movies != null) {
            for(String movie : movies){
                strB.append(movie).append("\n");
            }
        }
        listOfMovies.setText(strB.toString());
    }


    @FXML
    void generate() {

        LoadingTask task = new LoadingTask();
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        generatedXml.textProperty().bind(task.valueProperty());

        //generate logic function

//        readFile();
    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }

    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
    }
}
