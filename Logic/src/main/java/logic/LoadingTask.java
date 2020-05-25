package logic;

import javafx.concurrent.Task;
import org.jdom2.Document;
import logic.resources.XMLJDomFunctions;

import java.util.List;

public class LoadingTask extends Task {
    String found;

    public LoadingTask () {
    }

    @Override
    protected Object call() throws Exception {
        System.out.println("dupa");
        List<String> movies = Fetch.getAllTitles();
        int max = movies.size();
        Document doc = XMLJDomFunctions.readDocumentXML("movies.xml");
        for (int i = 0; i < max; i++) {
            System.out.println("dupa");
            XMLManipulation.addMovie(Fetch.findMovie(movies.get(i)), doc);
//            WHETHER SAVE XML FILE AFTER EVERY RECORD OR AT THE END
            updateProgress(i, max - 1);
            if (isCancelled()) {
                return found;
            }
        }
        System.out.println(getProgress());
        updateProgress(0, max - 1);
        found = XMLJDomFunctions.readDocumentToString(doc);
        return found;
    }
}
