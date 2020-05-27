package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.resources.XPathFunctions;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;

import java.util.LinkedList;
import java.util.List;

public class EditController {

    public VBox VBoxMovies;

    public TextArea feedBack;
    public TextField xmlFilePath;

    public ImageView feedbackIcon;

    @FXML
    void initialize() {
        xmlFilePath.setText("movies.xml");
    }

    @FXML
    void getMovies() {
        setListOfMovies();
    }

    private void setListOfMovies() {
        List<String> movies = null;
        List<Button> buttons = new LinkedList<>();

        try {
            XdmValue execution = XPathFunctions.executeXpath("//movie/title", xmlFilePath.getText());
            if (execution != null) {
                movies = XPathFunctions.showResults(execution);
                setCorrect();
            } else {
                setWrong();
                feedBack.setText("File not found!");
            }

        } catch (SaxonApiException e) {
            feedBack.setText(e.getMessage());
            e.printStackTrace();
            setWrong();
        }

        VBoxMovies.getChildren().clear();
        if (movies != null) {
            for (String movie : movies) {

                Button b = new Button();
                b.setId(movie);
                b.setText(movie);

                b.setPrefWidth(215);
                b.setWrapText(true);

                b.setOnAction((e)->{
                    Button currButton = (Button) e.getSource();
                    loadMovieInfo(currButton.getId());
                });

                buttons.add(b);
            }
        }
        VBoxMovies.getChildren().addAll(buttons);
    }

    private void loadMovieInfo(String title){

    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }

    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
        feedBack.setText("Movies loaded correctly");
    }

}
