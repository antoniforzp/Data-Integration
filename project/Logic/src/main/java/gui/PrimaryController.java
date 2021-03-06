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

    //tabs
    private final ArrayList<Pane> views = new ArrayList<>();

    public Pane moviesView;

    public Pane generateView;
    public Pane editView;
    public Pane validateView;

    public Pane toXmlView;
    public Pane toTxtView;
    public Pane toHtmlView;

    public Pane xPathView;

    public MenuButton xmlDropdown;
    public MenuButton transformDropdown;
    public Button searchButton;
    public Button exitButton;

    @FXML
    void initialize() {
        views.add(moviesView);

        views.add(generateView);
        views.add(editView);
        views.add(validateView);

        views.add(toXmlView);
        views.add(toTxtView);
        views.add(toHtmlView);

        views.add(xPathView);

        hideAllTabs();

        moviesView.setVisible(true);
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
    void goMovies() {
        hideAllTabs();
        moviesView.setVisible(true);
    }

    //xml operations

    @FXML
    void goGenerate() {
        hideAllTabs();
        generateView.setVisible(true);
    }

    @FXML
    void goEdit() {
        hideAllTabs();
        editView.setVisible(true);
    }

    @FXML
    void goValidate() {
        hideAllTabs();
        validateView.setVisible(true);
    }

//    to transforms

    @FXML
    void goToHtml() {
        hideAllTabs();
        toHtmlView.setVisible(true);
    }

    @FXML
    void goToTxt() {
        hideAllTabs();
        toTxtView.setVisible(true);
    }

    @FXML
    void goToXml() {
        hideAllTabs();
        toXmlView.setVisible(true);
    }

//    to xpath

    @FXML
    void goXpath() {
        hideAllTabs();
        xPathView.setVisible(true);
    }

//    exit

    @FXML
    void exit() {
        System.exit(0);
    }
}

