package gui.tabs;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ValidateController {

    public TextArea dtdFeedback;
    public TextArea xsdFeedback;

    public ImageView dtdFeedbackIcon;
    public ImageView xsdFeedbackIcon;

    public ImageView dtdFileIcon;
    public ImageView xsdFileIcon;

    @FXML
    void initialize() {
        dtdFeedback.setEditable(false);
        xsdFeedback.setEditable(false);
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

//            setWrong(dtdFeedbackIcon); -- ustawia ikonkę OK
//            setCorrect(dtdFeedbackIcon); -- ustawia ikonkę WRONG

            //daj ifa jest -1 0 1
            if (true) {
                dtdFeedback.setText("Validation performed correctly");
                setCorrect(dtdFeedbackIcon);
            }

        } catch (Exception e) {
            dtdFeedback.setText(e.getMessage());
            setWrong(dtdFeedbackIcon);
        }
    }

    @FXML
    void validateXsd() {
        try {

            //daj ifa jest -1 0 1
            if (true) {
                xsdFeedback.setText("Validation performed correctly");
                setCorrect(xsdFeedbackIcon);
            }

        } catch (Exception e) {
            xsdFeedback.setText(e.getMessage());
            setWrong(xsdFeedbackIcon);
        }
    }
}
