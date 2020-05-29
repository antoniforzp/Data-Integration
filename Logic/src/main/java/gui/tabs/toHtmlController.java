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
import org.jdom2.transform.XSLTransformException;

import java.util.HashMap;
import java.util.Map;

public class toHtmlController {

    public TextField xsltPath;
    public TextField htmlOutPath;
    public TextField xmlInPath;

    public ComboBox<String> filesCombo;

    public ImageView feedbackIcon;

    public TextArea output;


    private final Map<toHtmlController.optionsEnum, String> map = new HashMap<>();

    enum optionsEnum {
        toHtml1,
        toHtml2
    }

    public toHtmlController() {
        map.put(toHtmlController.optionsEnum.toHtml1, "get movie posters and directors");
        map.put(toHtmlController.optionsEnum.toHtml2, "get list of movies by box office");
    }

    private ObservableList<String> getAllOptionsDescriptions() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (Map.Entry<toHtmlController.optionsEnum, String> pair : map.entrySet()) {
            temp.add(pair.getValue());
        }
        return temp;
    }

    private toHtmlController.optionsEnum getKey(String value) {
        for (Map.Entry<toHtmlController.optionsEnum, String> pair : map.entrySet()) {
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
        toHtmlController.optionsEnum option = getKey(filesCombo.getValue());
        String transformDirectory = "files/transf/";
        String outputDirectory = "files/outputs/";
        if (option != null) {
            switch (option) {
                case toHtml1:
                    xsltPath.setText(transformDirectory + "toHtml_1.xsl");
                    htmlOutPath.setText(outputDirectory + "html_output_1.html");
                    break;
                case toHtml2:
                    xsltPath.setText(transformDirectory + "toHtml_2.xsl");
                    htmlOutPath.setText(outputDirectory + "html_output_2.html");
                    break;
            }
        }
    }

    @FXML
    void transform() {
        try {
            output.setText(XSLTLogic.toHtml(xmlInPath.getText(), xsltPath.getText(), htmlOutPath.getText()));
            setCorrect();
        } catch (XSLTransformException e) {
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
