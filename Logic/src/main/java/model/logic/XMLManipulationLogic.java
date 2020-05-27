package model.logic;

import model.data.Movie;
import model.resources.XPathFunctions;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

public class XMLManipulationLogic {
    public static Document addMovie(Movie movie, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }


        try {
            String xp = "//movie[title=\"" + movie.getTitle() + "\"]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res == null || res.size() == 0) {
                Element newMovie = new Element("movie");
                Element title = new Element("title").addContent(movie.getTitle());
                Element cover = new Element("cover").addContent(movie.getCover());
                Element year = new Element("year").addContent(String.valueOf(movie.getYear()));
                Element releaseDate = new Element("releaseDate").addContent(String.valueOf(movie.getReleaseDate()));
                Attribute releaseCountry = new Attribute("country", "USA");
                releaseDate.setAttribute(releaseCountry);
                Element countries = new Element("productionCountries");
                for (String s : movie.getCountry()) {
                    Element country = new Element("country").addContent(s);
                    countries.addContent(country);
                }
                Element director = new Element("director").addContent(movie.getDirector());
                Element cast = new Element("cast");
                for (String s : movie.getCast()) {
                    Element actor = new Element("actor").addContent(s);
                    cast.addContent(actor);
                }
                Element duration = new Element("duration").addContent(String.valueOf(movie.getDuration()));
                Element distribution = new Element("distribution").addContent(movie.getDistribution());
                Element languages = new Element("languages");
                for (String s : movie.getLanguage()) {
                    Element language = new Element("language").addContent(s);
                    languages.addContent(language);
                }
                Element music = new Element("music").addContent(movie.getMusic());
                Element boxOffice = new Element("boxOffice").addContent(String.valueOf(movie.getBoxOffice()));
                Attribute currency = new Attribute("currency", "$");
                boxOffice.setAttribute(currency);

                newMovie.addContent(title);
                newMovie.addContent(cover);
                newMovie.addContent(year);
                newMovie.addContent(releaseDate);
                newMovie.addContent(countries);
                newMovie.addContent(director);
                newMovie.addContent(cast);
                newMovie.addContent(duration);
                newMovie.addContent(distribution);
                newMovie.addContent(languages);
                newMovie.addContent(music);
                newMovie.addContent(boxOffice);

                root.addContent(newMovie);
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static Document editMovie(String oldTitle, Movie movie, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }


        try {
            String xp = "//movie[title=\"" + oldTitle + "\"]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            System.out.println(res.size());
            if (res.size() == 1) {
                System.out.println(res.itemAt(0).getStringValue());


//
//                Element newMovie = new Element("movie");
//                Element title = new Element("title").addContent(movie.getTitle());
//                Element cover = new Element("cover").addContent(movie.getCover());
//                Element year = new Element("year").addContent(String.valueOf(movie.getYear()));
//                Element releaseDate = new Element("releaseDate").addContent(String.valueOf(movie.getReleaseDate()));
//                Attribute releaseCountry = new Attribute("country", "USA");
//                releaseDate.setAttribute(releaseCountry);
//                Element countries = new Element("productionCountries");
//                for (String s : movie.getCountry()) {
//                    Element country = new Element("country").addContent(s);
//                    countries.addContent(country);
//                }
//                Element director = new Element("director").addContent(movie.getDirector());
//                Element cast = new Element("cast");
//                for (String s : movie.getCast()) {
//                    Element actor = new Element("actor").addContent(s);
//                    cast.addContent(actor);
//                }
//                Element duration = new Element("duration").addContent(String.valueOf(movie.getDuration()));
//                Element distribution = new Element("distribution").addContent(movie.getDistribution());
//                Element languages = new Element("languages");
//                for (String s : movie.getLanguage()) {
//                    Element language = new Element("language").addContent(s);
//                    languages.addContent(language);
//                }
//                Element music = new Element("music").addContent(movie.getMusic());
//                Element boxOffice = new Element("boxOffice").addContent(String.valueOf(movie.getBoxOffice()));
//                Attribute currency = new Attribute("currency", "$");
//                boxOffice.setAttribute(currency);
//
//                newMovie.addContent(title);
//                newMovie.addContent(cover);
//                newMovie.addContent(year);
//                newMovie.addContent(releaseDate);
//                newMovie.addContent(countries);
//                newMovie.addContent(director);
//                newMovie.addContent(cast);
//                newMovie.addContent(duration);
//                newMovie.addContent(distribution);
//                newMovie.addContent(languages);
//                newMovie.addContent(music);
//                newMovie.addContent(boxOffice);
//
//                root.addContent(newMovie);
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
