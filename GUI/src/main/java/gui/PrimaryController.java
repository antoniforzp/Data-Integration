package gui;

import java.io.IOException;
import javafx.fxml.FXML;
import logic.data.Movie;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

