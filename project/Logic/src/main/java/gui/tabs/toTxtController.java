package gui.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.logic.XSLTLogic;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class toTxtController {

    public TextField xsltPath;
    public TextField txtOutPath;
    public TextField xmlInPath;

    public ComboBox<String> filesCombo;

    public ImageView feedbackIcon;

    public TextArea output;

    private final Map<toTxtController.optionsEnum, String> map = new HashMap<>();

    enum optionsEnum {
        toTxt1,
        toTxt2
    }

    public toTxtController() {
        map.put(toTxtController.optionsEnum.toTxt1, "list of movies by given production country");
        map.put(toTxtController.optionsEnum.toTxt2, "list of movies in english sorted by year production");
    }

    private ObservableList<String> getAllOptionsDescriptions() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (Map.Entry<toTxtController.optionsEnum, String> pair : map.entrySet()) {
            temp.add(pair.getValue());
        }
        return temp;
    }

    private toTxtController.optionsEnum getKey(String value) {
        for (Map.Entry<toTxtController.optionsEnum, String> pair : map.entrySet()) {
            if (pair.getValue().equals(value)) return pair.getKey();
        }
        return null;
    }

    @FXML
    void initialize() {
        xmlInPath.setText("movies.xml");
        filesCombo.getItems().addAll(getAllOptionsDescriptions());
    }

    @FXML
    void selectXSLT() {
        toTxtController.optionsEnum option = getKey(filesCombo.getValue());
        String transformDirectory = "files/transf/";
        String outputDirectory = "files/outputs/";

        if (option != null) {
            switch (option) {
                case toTxt1:
                    xsltPath.setText(transformDirectory + "toTxt_1.xsl");
                    txtOutPath.setText(outputDirectory + "txt_output_1.txt");
                    break;
                case toTxt2:
                    xsltPath.setText(transformDirectory + "toTxt_2.xsl");
                    txtOutPath.setText(outputDirectory + "txt_output_2.txt");
                    break;
            }
        }
    }

    @FXML
    void transform() {
        try {
            output.setText(XSLTLogic.toTxt(xmlInPath.getText(), xsltPath.getText(), txtOutPath.getText()));
            setCorrect();
        } catch (TransformerException | IOException e) {
            output.setText(e.getMessage());
            e.printStackTrace();
            setWrong();
        }
    }

    private void setCorrect() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
    }

    private void setWrong() {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }
}
