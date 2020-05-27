package gui.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Fetch;
import model.LoadingTask;
import model.logic.GenerateLogic;

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

    private void setListOfMovies() {
        List<String> movies = Fetch.getAllTitles();
        StringBuilder strB = new StringBuilder();

        if (movies != null) {
            for (String movie : movies) {
                strB.append(movie).append("\n");
            }
        }
        listOfMovies.setText(strB.toString());
    }

    @FXML
    void generate() {
        setIdle();

        if (GenerateLogic.generate(textField.getText())) {
            progressBar.progressProperty().bind(GenerateLogic.getTask().progressProperty());
            generatedXml.textProperty().bind(GenerateLogic.getTask().valueProperty());

            GenerateLogic.getTask().setOnSucceeded(event -> {
                feedback.setText("XML generated correctly!");
                setCorrect();
            });
        } else {
            setWrong();
            feedback.setText("Wrong file, check the extension!");
        }
    }

    @FXML
    void cancel() {
        if (GenerateLogic.cancel()) {
            setWrong();
            feedback.setText("operation cancelled!");
        }
    }

    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }

    private void setIdle() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/question.png")));
    }

}
