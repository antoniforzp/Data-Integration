package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.logic.XSLTLogic;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public class toTxtController {

    public TextField xsltPath;
    public TextField txtOutPath;
    public TextField xmlInPath;

    public ComboBox<String> filesCombo;

    public ImageView feedbackIcon;

    public TextArea output;

    @FXML
    void initialize() {
        xmlInPath.setText("movies.xml");
        txtOutPath.setText("txt_output.txt");

        filesCombo.getItems().add("transf_txt.xsl");

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
