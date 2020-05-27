package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.logic.ValidateLogic;

public class ValidateController {

    public TextArea dtdFeedback;
    public TextArea xsdFeedback;

    public ImageView dtdFeedbackIcon;
    public ImageView xsdFeedbackIcon;

    public TextField xmlPath;
    public TextField dtdPath;
    public TextField xsdPath;

    public

    @FXML
    void initialize() {
        dtdFeedback.setEditable(false);
        xsdFeedback.setEditable(false);

        xmlPath.setText("movies.xml");
        dtdPath.setText("dtd jakieś");
        xsdPath.setText("xsd jakieś");
    }

    private void setWrong(ImageView feedbackIcon) {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/cross.png")));
    }

    private void setCorrect(ImageView feedbackIcon) {
        feedbackIcon.setImage(new Image(getClass().getResourceAsStream("img/tick.png")));
    }

    @FXML
    void validateDtd() {
        try {
            switch (ValidateLogic.validateDTD(xmlPath.getText(), dtdPath.getText())) {
                case 1:
                    dtdFeedback.setText("Validation performed correctly");
                    setCorrect(dtdFeedbackIcon);
                    break;
                case -1:
                    dtdFeedback.setText("Validation failed");
                    setWrong(dtdFeedbackIcon);
                    break;
                case 0: {
                    dtdFeedback.setText("Something went wrong");
                    setWrong(dtdFeedbackIcon);
                    break;
                }
            }
        } catch (Exception e) {
            dtdFeedback.setText(e.getMessage());
            setWrong(dtdFeedbackIcon);
        }
    }

    @FXML
    void validateXsd() {
        try {
            switch (ValidateLogic.validateXSD(xmlPath.getText(), xsdPath.getText())) {
                case 1:
                    dtdFeedback.setText("Validation performed correctly");
                    setCorrect(xsdFeedbackIcon);
                    break;
                case -1:
                    dtdFeedback.setText("Validation failed");
                    setWrong(xsdFeedbackIcon);
                    break;
                case 0: {
                    dtdFeedback.setText("Something went wrong");
                    setWrong(xsdFeedbackIcon);
                    break;
                }
            }
        } catch (Exception e) {
            dtdFeedback.setText(e.getMessage());
            setWrong(dtdFeedbackIcon);
        }
    }
}
