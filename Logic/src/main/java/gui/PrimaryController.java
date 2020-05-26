package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class PrimaryController {

    private static PrimaryController instance;

    public PrimaryController getInstance() {
        if (instance == null) {
            instance = new PrimaryController();
        }
        return instance;
    }

    //tabs
    public ArrayList<Pane> views = new ArrayList<>();
    public Pane generateView;
    public Pane moviesView;

    public Button moviesButton;
    public MenuButton xmlDropdown;
    public MenuButton transformDropdown;
    public Button searchButton;
    public Button exitButton;

    @FXML
    void initialize() {
        views.add(generateView);

        setUpIcons();
        hideAllTabs();

    }

    private void setUpIcons() {

        ImageView xmlIcon = new ImageView(new Image(getClass().getResourceAsStream("img/xml.png")));
        xmlIcon.setFitHeight(20);
        xmlIcon.setFitWidth(20);
        xmlDropdown.setGraphic(xmlIcon);

        ImageView transformIcon = new ImageView(new Image(getClass().getResourceAsStream("img/transform.png")));
        transformIcon.setFitHeight(20);
        transformIcon.setFitWidth(20);
        transformDropdown.setGraphic(transformIcon);

        ImageView searchIcon = new ImageView(new Image(getClass().getResourceAsStream("img/search.png")));
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);
        searchButton.setGraphic(searchIcon);

        ImageView exitIcon = new ImageView(new Image(getClass().getResourceAsStream("img/exit.png")));
        exitIcon.setFitHeight(20);
        exitIcon.setFitWidth(20);
        exitButton.setGraphic(exitIcon);
    }

    private void hideAllTabs() {
        for (Pane p : views) {
            p.setVisible(false);
        }
    }

    public static void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    @FXML
    void goGenerate() {
        generateView.setVisible(true);
    }
}

