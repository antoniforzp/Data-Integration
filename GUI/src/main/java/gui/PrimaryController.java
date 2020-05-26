package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController {

    public MenuButton xmlDropdown;
    public MenuButton transformDropdown;
    public Button searchButton;
    public Button exitButton;

    @FXML
    void initialize(){
        setUpIcons();
    }

    private void setUpIcons(){

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
}

