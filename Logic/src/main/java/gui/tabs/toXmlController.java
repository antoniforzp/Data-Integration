package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.logic.XSLTLogic;
import org.jdom2.transform.XSLTransformException;

public class toXmlController {

    public TextField xsltPath;
    public TextField xmlOutPath;
    public TextField xmlInPath;

    public ComboBox<String> filesCombo;

    public ImageView feedbackIcon;

    public TextArea output;

    @FXML
    void initialize() {
        xmlInPath.setText("movies.xml");
        xmlOutPath.setText("xml_output.xml");

        filesCombo.getItems().add("transf_xml.xsl");

        if (filesCombo.getItems().size() >= 1) {
            xsltPath.setText(filesCombo.getItems().get(0));
        }
    }

    @FXML
    void selectXSLT() {
        xsltPath.setText(filesCombo.getValue());
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
