package model.logic;

import model.data.Movie;
import model.resources.XMLJDomFunctions;
import model.resources.XPathFunctions;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.commons.lang3.StringEscapeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XPathLogic {
    public static Movie getMovieByTitle(String title, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        try {
            String xp = "//movie[title=\"" + title + "\"]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() == 1) {

                Document newDoc = XMLJDomFunctions.readStringToDocument(res.toString());
                root = newDoc.getRootElement();
                return getMovieFromDocument(root);
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Movie> getMoviesByDirector(String director, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        List<Movie> found = new ArrayList<>();

        try {
            String xp = "//movie[director=\"" + director + "\"]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() > 0) {
                for (XdmItem item : res) {
                    Document newDoc = XMLJDomFunctions.readStringToDocument(item.toString());
                    root = newDoc.getRootElement();
                    found.add(getMovieFromDocument(root));
                }
                return found;
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Movie> getMoviesByActor(String actor, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        List<Movie> found = new ArrayList<>();

        try {
            String xp = "//movie[cast/actor=\"" + actor + "\"]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() > 0) {
                for (XdmItem item : res) {
                    Document newDoc = XMLJDomFunctions.readStringToDocument(item.toString());
                    root = newDoc.getRootElement();
                    found.add(getMovieFromDocument(root));
                }
                return found;
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Movie> getMoviesByDuration(int min, int max, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        List<Movie> found = new ArrayList<>();

        try {
            String xp = "//movie[duration>" + min + " and duration<" + max + "]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() > 0) {
                for (XdmItem item : res) {
                    Document newDoc = XMLJDomFunctions.readStringToDocument(item.toString());
                    root = newDoc.getRootElement();
                    found.add(getMovieFromDocument(root));
                }
                return found;
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Movie> getMoviesByCountry(String country, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        List<Movie> found = new ArrayList<>();

        try {
            String xp = "//movie[productionCountries/country=\"" + country + "\"]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() > 0) {
                for (XdmItem item : res) {
                    Document newDoc = XMLJDomFunctions.readStringToDocument(item.toString());
                    root = newDoc.getRootElement();
                    found.add(getMovieFromDocument(root));
                }
                return found;
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Movie> getMoviesByBoxOffice(int min, int max, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        List<Movie> found = new ArrayList<>();

        try {
            String xp = "//movie[boxOffice>" + min + " and boxOffice<" + max + "]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() > 0) {
                for (XdmItem item : res) {
                    Document newDoc = XMLJDomFunctions.readStringToDocument(item.toString());
                    root = newDoc.getRootElement();
                    found.add(getMovieFromDocument(root));
                }
                return found;
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Movie> getMoviesByYear(int min, int max, Document doc) {
        Element root;
        if (doc == null) {
            root = new Element("movies");
            doc = new Document(root);
        } else {
            root = doc.getRootElement();
        }

        List<Movie> found = new ArrayList<>();

        try {
            String xp = "//movie[year>" + min + " and year<" + max + "]";
            XdmValue res = XPathFunctions.executeXpath(xp, "movies.xml");
            if (res.size() > 0) {
                for (XdmItem item : res) {
                    Document newDoc = XMLJDomFunctions.readStringToDocument(item.toString());
                    root = newDoc.getRootElement();
                    found.add(getMovieFromDocument(root));
                }
                return found;
            }
        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Movie getMovieFromDocument(Element root) {
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat(Format.getCompactFormat());
        try {

            String title = outp.outputString(root.getChild("title").getContent());
            title = StringEscapeUtils.unescapeHtml4(title);

            String cover = outp.outputString(root.getChild("cover").getContent());
            int year = Integer.parseInt(outp.outputString(root.getChild("year").getContent()));
            Date releaseDate = null;
            releaseDate = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(outp.outputString(root.getChild("releaseDate").getContent()));

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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
