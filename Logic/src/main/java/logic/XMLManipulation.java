package logic;

import logic.data.Movie;
import org.jdom2.Document;
import org.jdom2.Element;

public class XMLManipulation {
    public static Document addMovie(Movie movie, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        Element newMovie = new Element("movie");
        Element title = new Element("title").addContent(movie.getTitle());
        newMovie.addContent(title);

        doc.addContent(newMovie);

        //
        //  DECIDE ON XML STRUCTURE
        //

        return doc;
    }
}
