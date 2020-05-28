package gui.tabs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

    public Label feedBack;
    public TextField xmlFilePath;

    public ImageView feedbackIcon;


    public VBox vBox;

    List<Text> texts = new ArrayList<>();
    List<Node> textEditors = new ArrayList<>();

    int lastEdit = 0;

    String oldTitle = null;

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

    private void loadMovieInfo(String movieTitle) {
        Document doc = XMLJDomFunctions.readDocumentXML("movies.xml");
        Movie movie = XMLManipulationLogic.getMovieByTitle(movieTitle, doc);

        assert movie != null;
        generateTextFields(movie);
    }

    private void generateTextFields(Movie movie) {
        vBox.getChildren().clear();
        texts.clear();
        textEditors.clear();

        texts.add(new Text("Title:"));
        texts.add(new Text("Cover link:"));
        texts.add(new Text("Year:"));
        texts.add(new Text("Release date:"));
        texts.add(new Text("Country:")); //4
        texts.add(new Text("Director:"));
        texts.add(new Text("Cast:")); //6
        texts.add(new Text("Distribution:"));
        texts.add(new Text("Duration:"));
        texts.add(new Text("Language:"));
        texts.add(new Text("Music composer:"));
        texts.add(new Text("Box office:"));

        //0 - TITLE
        TextField title = new TextField();
        title.setText(movie.getTitle());
        textEditors.add(title);
        oldTitle = movie.getTitle();

        //1 - Cover link
        TextField coverLink = new TextField();
        coverLink.setText(movie.getCover());
        textEditors.add(coverLink);

        //2 - Year
        TextField year = new TextField();
        year.setText(String.valueOf(movie.getYear()));
        textEditors.add(year);

        //3 - Release date
        TextField releaseDate = new TextField();
        releaseDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(movie.getReleaseDate()));
        textEditors.add(releaseDate);

        //4 - Countries
        if (movie.getCountry().size() <= 1) {
            TextField country = new TextField();
            country.setText(movie.getCountry().get(0));
            textEditors.add(country);
        } else {
            textEditors.add(setupComboBoxCountry(movie));
        }

        //5 - Director
        TextField director = new TextField();
        director.setText(movie.getDirector());
        textEditors.add(director);

        //6 - Cast
        if (movie.getCast().size() <= 1) {
            TextField cast = new TextField();
            cast.setText(movie.getCast().get(0));
            textEditors.add(cast);
        } else {
            textEditors.add(setupComboBoxCast(movie));
        }

        //7 - Distribution
        TextField distribution = new TextField();
        distribution.setText(movie.getDistribution());
        textEditors.add(distribution);

        //8 - Duration
        TextField duration = new TextField();
        duration.setText(String.valueOf(movie.getDuration()));
        textEditors.add(duration);

        //9 - Language
        if (movie.getLanguage().size() <= 1) {
            TextField language = new TextField();
            language.setText(movie.getLanguage().get(0));
            textEditors.add(language);
        } else {
            textEditors.add(setupComboBoxLanguage(movie));
        }

        //10 - Music composer
        TextField musicComposer = new TextField();
        musicComposer.setText(movie.getMusic());
        textEditors.add(musicComposer);

        //11 - Box office
        TextField boxOffice = new TextField();
        boxOffice.setText(String.valueOf(movie.getBoxOffice()));
        textEditors.add(boxOffice);

        int textIndex = 0;
        int editorIndex = 0;

        for (int i = 0; i < texts.size() + textEditors.size(); i++) {
            if (i % 2 == 0) {
                vBox.getChildren().add(texts.get(textIndex++));
            } else {
                vBox.getChildren().add(textEditors.get(editorIndex++));
            }
        }
    }

    private ComboBox<String> setupComboBoxCountry(Movie movie) {
        ComboBox<String> countries = new ComboBox<>();
        countries.getItems().addAll(movie.getCountry());
        countries.setEditable(true);
        countries.setPromptText("choose country");

        countries.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            ObservableList<String> countriesList = countries.getItems();
            int row = countriesList.indexOf(newText);
            if (row == -1) {
                for (int i = 0; i < countriesList.size(); i++) {
                    if (countriesList.get(i).equals(oldText)) {
                        countriesList.set(i, newText);
                    }
                }
            }
            lastEdit = row;
            countries.setItems(countriesList);
        });

        return countries;
    }

    private ComboBox<String> setupComboBoxCast(Movie movie) {
        ComboBox<String> cast = new ComboBox<>();
        cast.getItems().addAll(movie.getCast());
        cast.setEditable(true);
        cast.setPromptText("choose actor");


        cast.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            ObservableList<String> actors = cast.getItems();
            int row = actors.indexOf(newText);
            if (row == -1) {
                for (int i = 0; i < actors.size(); i++) {
                    if (actors.get(i).equals(oldText)) {
                        actors.set(i, newText);
                    }
                }
            }
            lastEdit = row;
            cast.setItems(actors);
        });

        return cast;
    }

    private ComboBox<String> setupComboBoxLanguage(Movie movie) {
        ComboBox<String> language = new ComboBox<>();
        language.getItems().addAll(movie.getLanguage());
        language.setEditable(true);
        language.setPromptText("choose language");


        language.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            ObservableList<String> languageItems = language.getItems();
            int row = languageItems.indexOf(newText);
            if (row == -1) {
                for (int i = 0; i < languageItems.size(); i++) {
                    if (languageItems.get(i).equals(oldText)) {
                        languageItems.set(i, newText);
                    }
                }
            }
            lastEdit = row;
            language.setItems(languageItems);
        });

        return language;
    }

    @FXML
    public void saveMovieInfo() {
        Movie movie = null;
        try {
            movie = new Movie(((TextField)textEditors.get(0)).getText(), ((TextField)textEditors.get(1)).getText(), Integer.parseInt(((TextField)textEditors.get(2)).getText()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(((TextField)textEditors.get(3)).getText()), ((ComboBox)textEditors.get(4)).getItems(), ((TextField)textEditors.get(5)).getText(),
                    ((ComboBox)textEditors.get(6)).getItems(), Integer.parseInt(((TextField)textEditors.get(7)).getText()), ((TextField)textEditors.get(8)).getText(),
                    ((ComboBox)textEditors.get(9)).getItems(), ((TextField)textEditors.get(10)).getText(), Integer.parseInt(((TextField)textEditors.get(11)).getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Document doc = XMLJDomFunctions.readDocumentXML("movies.xml");
        doc = XMLManipulationLogic.editMovie(oldTitle, movie, doc);
        XMLJDomFunctions.writeDocumentToFile(doc, "movies.xml");

        setListOfMovies();
    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }

    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
        feedBack.setText("Movies loaded correctly");
    }

}
