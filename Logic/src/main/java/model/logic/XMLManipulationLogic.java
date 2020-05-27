package model.logic;

import model.data.Movie;
import model.resources.XMLJDomFunctions;
import model.resources.XPathFunctions;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
                root.addContent(getNewMovieNode(movie));
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
                Document newDoc = XMLJDomFunctions.readStringToDocument(res.toString());
                Element oldMovie = newDoc.getRootElement();

                if (doc.removeContent(oldMovie)) {
                    root.addContent(getNewMovieNode(movie));
                }
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static Movie getMovieByTitle(String oldTitle, Document doc) {
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
            if (res.size() == 1) {
                Document newDoc = XMLJDomFunctions.readStringToDocument(res.toString());
                root = newDoc.getRootElement();

                XMLOutputter outp = new XMLOutputter();
                outp.setFormat(Format.getCompactFormat());
                StringWriter sw = new StringWriter();

                outp.output(root.getChild("title").getContent(), sw);
                String title = sw.toString();

                sw = new StringWriter();
                outp.output(root.getChild("cover").getContent(), sw);
                String cover = sw.toString();

                sw = new StringWriter();
                outp.output(root.getChild("year").getContent(), sw);
                int year = Integer.parseInt(sw.toString());

                sw = new StringWriter();
                outp.output(root.getChild("releaseDate").getContent(), sw);
                Date releaseDate = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(sw.toString());

                List<Element> elements = root.getChild("productionCountries").getChildren();
                List<String> countries = new ArrayList<>();
                for(Element e: elements) {
                    sw = new StringWriter();
                    outp.output(e.getContent(), sw);
                    countries.add(sw.toString());
                }

                sw = new StringWriter();
                outp.output(root.getChild("director").getContent(), sw);
                String director = sw.toString();

                elements = root.getChild("cast").getChildren();
                List<String> actors = new ArrayList<>();
                for(Element e: elements) {
                    sw = new StringWriter();
                    outp.output(e.getContent(), sw);
                    actors.add(sw.toString());
                }

                sw = new StringWriter();
                outp.output(root.getChild("duration").getContent(), sw);
                int duration = Integer.parseInt(sw.toString());

                sw = new StringWriter();
                outp.output(root.getChild("distribution").getContent(), sw);
                String distribution = sw.toString();

                elements = root.getChild("languages").getChildren();
                List<String> languages = new ArrayList<>();
                for(Element e: elements) {
                    sw = new StringWriter();
                    outp.output(e.getContent(), sw);
                    languages.add(sw.toString());
                }

                sw = new StringWriter();
                outp.output(root.getChild("music").getContent(), sw);
                String music = sw.toString();

                sw = new StringWriter();
                outp.output(root.getChild("boxOffice").getContent(), sw);
                int boxOffice = Integer.parseInt(sw.toString());

                Movie movie = new Movie(title, cover, year, releaseDate, countries, director, actors, duration, distribution, languages, music, boxOffice);
                return movie;
            }
        } catch (SaxonApiException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Element getNewMovieNode(Movie movie) {
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

        return newMovie;
    }
}
