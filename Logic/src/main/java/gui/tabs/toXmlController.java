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

public class toXmlController {

    public TextField xsltPath;
    public TextField xmlOutPath;
    public TextField xmlInPath;

    public ComboBox<String> filesCombo;

    public ImageView feedbackIcon;

    public TextArea output;

    private final Map<toXmlController.optionsEnum, String> map = new HashMap<>();

    enum optionsEnum {
        toXml1,
        toXml2
    }

    public toXmlController() {
        map.put(toXmlController.optionsEnum.toXml1, "get films of a given director");
        map.put(toXmlController.optionsEnum.toXml2, "<???>");
    }

    private ObservableList<String> getAllOptionsDescriptions() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (Map.Entry<toXmlController.optionsEnum, String> pair : map.entrySet()) {
            temp.add(pair.getValue());
        }
        return temp;
    }

    private toXmlController.optionsEnum getKey(String value) {
        for (Map.Entry<toXmlController.optionsEnum, String> pair : map.entrySet()) {
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
        toXmlController.optionsEnum option = getKey(filesCombo.getValue());
        String transformDirectory = "files/transf/";
        String outputDirectory = "files/outputs/";
        if (option != null) {
            switch (option) {
                case toXml1:
                    xsltPath.setText(transformDirectory + "toXml_1.xsl");
                    xmlOutPath.setText(outputDirectory + "xml_output_1.xml");
                    break;
                case toXml2:
                    xsltPath.setText(transformDirectory + "toXml_2.xsl");
                    xmlOutPath.setText(outputDirectory + "xml_output_2.xml");
                    break;
            }
        }
    }

    @FXML
    void transform() {
        try {
            output.setText(XSLTLogic.toXml(xmlInPath.getText(), xsltPath.getText(), xmlOutPath.getText()));
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
