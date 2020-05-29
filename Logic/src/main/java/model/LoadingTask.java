package model;

import javafx.concurrent.Task;
import model.data.Movie;
import model.logic.XMLManipulationLogic;
import org.jdom2.Document;
import model.resources.XMLJDomFunctions;

import java.util.List;

public class LoadingTask extends Task {
    String found;
    String filename;

    public LoadingTask(String filename) {
        this.filename = filename;
    }

    @Override
    protected Object call() {
        List<String> movies = Fetch.getAllTitles();
        int max = 0;
        if (movies != null) {
            max = movies.size();
        }
        updateProgress(0, max);
        Document doc = XMLJDomFunctions.readDocumentXML(filename);
        for (int i = 0; i < max; i++) {
            doc = XMLManipulationLogic.addMovie(movies.get(i), doc);
            System.out.println("\n\n");
//            WHETHER SAVE XML FILE AFTER EVERY RECORD OR AT THE END
            XMLJDomFunctions.writeDocumentToFile(doc, filename);
            found = XMLJDomFunctions.readDocumentToString(doc);

            updateProgress(i + 1, max);
//            break;
            if (isCancelled()) {
                return found;
            }
        }

//        System.out.println(getProgress());
        updateProgress(0, max - 1);
        return found;
    }
}
