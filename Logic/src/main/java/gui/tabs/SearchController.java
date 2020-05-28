package gui.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.FileChecker;
import model.data.Movie;
import model.logic.XPathLogic;

import java.io.IOException;
import java.util.*;


public class SearchController {

    public ImageView feedbackIcon;

    public TextField xmlPath;

    public Text phraseText;
    public Text minText;
    public Text maxText;

    public TextField phrase;

    public TextField minTextField;
    public TextField maxTextField;

    public TextArea output;

    public Label alert;

    public ComboBox<String> optionsCombo;
    private final Map<optionsEnum, String> map = new HashMap<>();

    enum optionsEnum {
        allMovies,
        byTitle,
        byDirector,
        byActor,
        byDuration,
        byCountry,
        byBoxOffice,
        byYear
    }

    public SearchController() {
        map.put(optionsEnum.allMovies, "get all movies");
        map.put(optionsEnum.byTitle, "get movie by title");
        map.put(optionsEnum.byDirector, "get movie by director");
        map.put(optionsEnum.byActor, "get movie by actor");
        map.put(optionsEnum.byDuration, "get movie by duration");
        map.put(optionsEnum.byCountry, "get movie by country");
        map.put(optionsEnum.byBoxOffice, "get movie by box office");
        map.put(optionsEnum.byYear, "get movie by year");
    }

    private ObservableList<String> getAllOptionsDescriptions() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (Map.Entry<optionsEnum, String> pair : map.entrySet()) {
            temp.add(pair.getValue());
        }
        return temp;
    }

    private optionsEnum getKey(String value) {
        for (Map.Entry<optionsEnum, String> pair : map.entrySet()) {
            if (pair.getValue().equals(value)) return pair.getKey();
        }
        return null;
    }

    @FXML
    void initialize() {
        alert.setText("");
        optionsCombo.setPromptText("choose options");
        optionsCombo.setItems(getAllOptionsDescriptions());
        xmlPath.setText("movies.xml");
        output.setEditable(false);

        showPhrase();
        optionsCombo.valueProperty().addListener((observableValue, s, t1) -> {

            hideAllFields();
            if (t1.equals(map.get(optionsEnum.allMovies))) {
                hideAllFields();
            } else if (t1.equals(map.get(optionsEnum.byDuration)) ||
                    t1.equals(map.get(optionsEnum.byBoxOffice)) ||
                    t1.equals(map.get(optionsEnum.byYear))) {
                showMinMax();
            } else {
                showPhrase();
            }
        });
    }

    @FXML
    void search() {

        alert.setText("");
        if (minTextField.getText().equals("")) minTextField.setText("0");
        if (maxTextField.getText().equals("")) maxTextField.setText("0");

        try {

            if (!FileChecker.checkFile(xmlPath.getText())) {
                throw new IOException();
            }

            List<Movie> movies = searchOptions(Objects.requireNonNull(getKey(optionsCombo.getValue())),
                    phrase.getText(),
                    Integer.parseInt(minTextField.getText()),
                    Integer.parseInt(maxTextField.getText()),
                    xmlPath.getText());

            if (movies == null) {
                setWrong();
                output.setText("No movies found");
            } else {
                setCorrect();
                output.setText(movies.toString());
            }

        } catch (NumberFormatException e) {
            setWrong();
            output.setText("You need to give proper values");
        } catch (IOException e) {
            setWrong();
            output.setText("such xml file does not exist");
        }
    }

    private List<Movie> searchOptions(optionsEnum option, String phrase, int min, int max, String filename) {
        switch (option) {
            case allMovies:
                return XPathLogic.getAllMovies(filename);
            case byTitle:
                return XPathLogic.getMovieByTitle(phrase, filename);
            case byDirector:
                return XPathLogic.getMoviesByDirector(phrase, filename);
            case byActor:
                return XPathLogic.getMoviesByActor(phrase, filename);
            case byDuration:
                return XPathLogic.getMoviesByDuration(min, max, filename);
            case byCountry:
                return XPathLogic.getMoviesByCountry(phrase, filename);
            case byBoxOffice:
                return XPathLogic.getMoviesByBoxOffice(min, max, filename);
            case byYear:
                return XPathLogic.getMoviesByYear(min, max, filename);
        }
        return null;
    }

    private void hideAllFields() {
        phraseText.setVisible(false);
        phrase.setVisible(false);
        minText.setVisible(false);
        maxText.setVisible(false);
        minTextField.setVisible(false);
        maxTextField.setVisible(false);
    }

    private void showMinMax() {
        minText.setVisible(true);
        maxText.setVisible(true);
        minTextField.setVisible(true);
        maxTextField.setVisible(true);
    }

    private void showPhrase() {
        phraseText.setVisible(true);
        phrase.setVisible(true);
    }


    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
        alert.setText("xpath executed correctly");
    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }
}
