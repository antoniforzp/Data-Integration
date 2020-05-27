package gui.tabs;

import gui.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.Fetch;
import logic.ListOperator;

import java.io.IOException;
import java.util.List;

public class MoviesController {

    public GridPane grid;
    public TextField textField;
    public Text alert;

    private List<String> titles;

    public MoviesController() {
        titles = Fetch.getAllTitles();
    }

    @FXML
    void initialize() {
        resetAlert();
        updateGrid();
    }

    private void resetAlert(){
//        alert.setText("");
    }

    private void loadTitles(){
        titles = Fetch.getAllTitles();
    }

    private void resetGrid() {
        grid.getChildren().clear();
    }

    private void updateGrid() {
        loadTitles();

        int count = 0;
        resetGrid();
        for (String title : titles) {

            Text titleText = new Text(title);
            titleText.setWrappingWidth(350);

            grid.addRow(count + 1, new Text(count + 1 + ". "), titleText, getButton(count));
            count++;
        }
        grid.setVgap(10);
    }

    private Button getButton(int id) {
        Button b = new Button();

        b.setPrefHeight(25);
        b.setPrefWidth(25);
        b.setId(String.valueOf(id));

        b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/trash.png"))));

        b.setOnAction((e)->{
            Button current = (Button) e.getSource();
            deleteTitle(Integer.parseInt(current.getId()));
        });

        return b;
    }

    private void deleteTitle(int index){

        try {
            ListOperator.deleteTitle(index);
        } catch (IOException e) {
            PrimaryController.displayAlert(e.getMessage());
            e.printStackTrace();
        }

        updateGrid();
    }

    @FXML
    public void addTitle(){
        resetAlert();

        try {
            ListOperator.addTitle(textField.getText());
        } catch (IOException e) {
            PrimaryController.displayAlert(e.getMessage());
            e.printStackTrace();
        }

        updateGrid();
    }

}
