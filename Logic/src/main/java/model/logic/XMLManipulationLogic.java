package model.logic;

import model.data.Movie;
import model.resources.XMLJDomFunctions;
import model.resources.XPathFunctions;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmValue;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.xpath.XPath;
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
        Element oldMovie = null;
        for (Element e : root.getChildren()) {
            if (e.getChildText("title").compareTo(oldTitle) == 0) {
                oldMovie = e;
                break;
            }
        }
        if (root.removeContent(oldMovie))
            root.addContent(getNewMovieNode(movie));
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
            System.out.println(res.size());
            if (res.size() == 1) {

                Document newDoc = XMLJDomFunctions.readStringToDocument(res.toString());
                root = newDoc.getRootElement();

                XMLOutputter outp = new XMLOutputter();
                outp.setFormat(Format.getCompactFormat());

                String title = outp.outputString(root.getChild("title").getContent());
                title = StringEscapeUtils.unescapeHtml4(title);
                String cover = outp.outputString(root.getChild("cover").getContent());
                int year = Integer.parseInt(outp.outputString(root.getChild("year").getContent()));
                Date releaseDate = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(outp.outputString(root.getChild("releaseDate").getContent()));
                List<Element> elements = root.getChild("productionCountries").getChildren();
                List<String> countries = new ArrayList<>();
                for (Element e : elements) {
                    countries.add(outp.outputString(e.getContent()));
                }
                String director = outp.outputString(root.getChild("director").getContent());
                elements = root.getChild("cast").getChildren();
                List<String> actors = new ArrayList<>();
                for (Element e : elements) {
                    actors.add(outp.outputString(e.getContent()));
                }
                int duration = Integer.parseInt(outp.outputString(root.getChild("duration").getContent()));
                String distribution = outp.outputString(root.getChild("distribution").getContent());
                elements = root.getChild("languages").getChildren();
                List<String> languages = new ArrayList<>();
                for (Element e : elements) {
                    languages.add(outp.outputString(e.getContent()));
                }
                String music = outp.outputString(root.getChild("music").getContent());
                int boxOffice = Integer.parseInt(outp.outputString(root.getChild("boxOffice").getContent()));
                Movie movie = new Movie(title, cover, year, releaseDate, countries, director, actors, duration, distribution, languages, music, boxOffice);
                return movie;
            }
        } catch (SaxonApiException | ParseException e) {
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
