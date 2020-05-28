package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.data.Movie;
import model.logic.XMLManipulationLogic;
import model.resources.XMLJDomFunctions;
import model.resources.XPathFunctions;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EditController {

    public VBox VBoxMovies;

    public TextArea feedBack;
    public TextField xmlFilePath;

    public ImageView feedbackIcon;

    @FXML
    TextField titleTF;
    @FXML
    TextField yearTF;
    @FXML
    TextField releaseDateTF;
    @FXML
    TextField countryTF;
    @FXML
    TextField directorTF;
    @FXML
    TextField castTF;
    @FXML
    TextField durationTF;
    @FXML
    TextField languageTF;
    @FXML
    TextField musicTF;
    @FXML
    TextField boxOfficeTF;
    @FXML
    TextField coverTF;
    @FXML
    TextField distributionTF;

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

                b.setOnAction((e) -> {
                    Button currButton = (Button) e.getSource();
                    loadMovieInfo(currButton.getId());
                });

                buttons.add(b);
            }
        }
        VBoxMovies.getChildren().addAll(buttons);
    }

    private void loadMovieInfo(String title) {
        Document doc = XMLJDomFunctions.readDocumentXML("movies.xml");
        Movie movie = XMLManipulationLogic.getMovieByTitle(title, doc);
        titleTF.setText(movie.getTitle());
        yearTF.setText(String.valueOf(movie.getYear()));
        releaseDateTF.setText(new SimpleDateFormat("yyyy-MM-dd").format(movie.getReleaseDate()));
        countryTF.setText(movie.getCountry().toString());
        countryTF.setText(countryTF.getText(1, countryTF.getText().length() - 1));
        directorTF.setText(movie.getDirector());
        castTF.setText(movie.getCast().toString());
        castTF.setText(castTF.getText(1, castTF.getText().length() - 1));
        durationTF.setText(String.valueOf(movie.getDuration()));
        languageTF.setText(movie.getLanguage().toString());
        languageTF.setText(languageTF.getText(1, languageTF.getText().length() - 1));
        musicTF.setText(movie.getMusic());
        boxOfficeTF.setText(String.valueOf(movie.getBoxOffice()));
    }

    public void saveMovieInfo(String title) {
        Movie movie = null;
//        try {
//            movie = new Movie(titleTF.getText(), coverTF.getText(), Integer.parseInt(yearTF.getText()),
//                    new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateTF.getText()), countryTF.getChildren(), directorTF.getText(),
//                    castTF.getText(), durationTF.getText(), distributionTF.getText(),
//                    languageTF.getText(), musicTF.getText(), boxOfficeTF.getText());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Document doc = XMLJDomFunctions.readDocumentXML("movies.xml");
        doc = XMLManipulationLogic.editMovie(title, movie, doc);
        XMLJDomFunctions.writeDocumentToFile(doc, "movies.xml");
    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }

    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
        feedBack.setText("Movies loaded correctly");
    }

}
