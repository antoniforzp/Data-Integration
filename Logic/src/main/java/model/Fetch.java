package model;

import model.data.Movie;
import model.resources.HttpRequestFunctions;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fetch {

    static String listDirectory = "files/list/";
    static String downloadsDirectory = "files/downloads/";

    public static List<String> getAllTitles() {
        List<String> results = new ArrayList<>();
        try {
            FileReader listReader = new FileReader(listDirectory + "list.txt");
            BufferedReader listBufferedReader = new BufferedReader(listReader);
            String listMovie;
            while ((listMovie = listBufferedReader.readLine()) != null) {
                results.add(listMovie);
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Movie findMovie(String search) {
        List<String> results = new ArrayList<>();
        List<String> keyWords = new ArrayList<>();
        keyWords.add("film");
        keyWords.add("movie");
        String link = "";

        for (String s : keyWords) {
            HttpRequestFunctions.httpRequestSearch("en.wikipedia.org/w/index.php?cirrusUserTesting=glent_m0&search=", search, "+" + s + "&title=Special%3ASearch&go=Go&ns0=1", downloadsDirectory + "currentMovieSearch.html");

            String regexHeader = "search-result-heading.*$";
            String regexLink = "href=\"[a-zA-Z0-9_/()%.':]+\"";

            Pattern patternHeader = Pattern.compile(regexHeader);
            Pattern patternLink = Pattern.compile(regexLink);
            boolean searchFound = false;

            try {
                FileReader reader = new FileReader(downloadsDirectory + "currentMovieSearch.html");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Matcher matcherHeader = patternHeader.matcher(line);
                    if (matcherHeader.find()) {
                        line = matcherHeader.group();
                        searchFound = true;
                        break;
                    }
                }
                if (searchFound) {
                    Matcher matcherLink = patternLink.matcher(line);
                    if (matcherLink.find()) {
                        link = matcherLink.group().substring(6, matcherLink.group().length() - 1);
                        link = StringEscapeUtils.unescapeHtml4(link);
                    }
                }
                if (!link.isEmpty()) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (link.isEmpty()) {
            link = "/wiki/" + search.replace(" ", "_");
        }

        link = link.replace("%26", "&").replace("%27", "'");
        System.out.println(link);
        HttpRequestFunctions.httpRequest1("en.wikipedia.org", link, downloadsDirectory + "currentMovie.html");

        String regexB = "infobox vevent";
        String regexE = "</tbody>";
        Pattern patternB = Pattern.compile(regexB);
        Pattern patternE = Pattern.compile(regexE);

        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovie.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherB = patternB.matcher(line);
                Matcher matcherE = patternE.matcher(line);
                if (matcherB.find()) {
                    read = true;
                }
                if (read)
                    results.add(line);
                if (read && matcherE.find()) {
                    break;
                }
            }

            FileWriter writer = new FileWriter(downloadsDirectory + "currentMovieInfobox.html");
            if (results.size() == 0)
                return null;
            for (String s : results) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Movie(findTitle(), findCover(), Integer.parseInt(Objects.requireNonNull(findProductionYear())),
                findReleaseUsa(), findCountry(), findDirector(),
                findCast(), Integer.parseInt(Objects.requireNonNull(findRuntime())), findDistribution(),
                findLanguage(), findMusic(), findBoxOffice());
    }

    public static String findTitle() {
        String regex = ">[a-zA-Z0-9\\s&():.']+<";
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            line = StringEscapeUtils.unescapeHtml4(line);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                line = matcher.group().substring(1, matcher.group().length() - 1);
                line.replace("%26", "&").replace("%27", "'");
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findCover() {
        String regex = "src=\"\\S*\\b";
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                line = matcher.group().substring(5);
                return "https:" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findProductionYear() {
        String regex = "\\b\\d{4}\\b";
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date findReleaseUsa() {
        String regexSelector = "United States";
        String regex = "\\b\\d{4}-\\d{2}-\\d{2}\\b";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find()) {
                    break;
                }
            }
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(matcher.group());
            } else {
                reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
                bufferedReader = new BufferedReader(reader);
                while ((line = bufferedReader.readLine()) != null) {
                    matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(matcher.group());
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> findCountry() {
        List<String> results = new ArrayList<>();
        String regexStarring = "Country.*$";
        String regex = ">[A-Za-z\\s]+<";
        Pattern patternStarring = Pattern.compile(regexStarring);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            boolean end = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherStarring = patternStarring.matcher(line);
                if (matcherStarring.find() && !read) {
                    read = true;
                    line = matcherStarring.group();
                }
                if (read) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        if (matcher.group().compareTo(">Language<") == 0
                                || matcher.group().compareTo(">Budget<") == 0) {
                            end = true;
                            break;
                        }
                        results.add(matcher.group().substring(1, matcher.group().length() - 1));
                    }
                }
                if (end) {
                    break;
                }
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findDirector() {
        String regexSelector = "Directed by.*$";
        String regex = ">[A-Za-z\\s]+<";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find()) {
                    line = matcherSelector.group();
                    break;
                }
            }
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return matcher.group().substring(1, matcher.group().length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> findCast() {
        List<String> results = new ArrayList<>();
        String regexStarring = "Starring.*$";
        String regex = ">[A-Za-z\\s]+<";
        Pattern patternStarring = Pattern.compile(regexStarring);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            boolean end = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherStarring = patternStarring.matcher(line);
                if (matcherStarring.find() && !read) {
                    read = true;
                    line = matcherStarring.group();
                }
                if (read) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        if (matcher.group().compareTo(">Music by<") == 0
                                || matcher.group().compareTo(">Running time<") == 0
                                || matcher.group().compareTo(">Cinematography<") == 0) {
                            end = true;
                            break;
                        }
                        results.add(matcher.group().substring(1, matcher.group().length() - 1));
                    }
                }
                if (end) {
                    break;
                }
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findRuntime() {
        String regexSelector = "Running time.*$";
        String regex = ">[0-9\\s]+";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find()) {
                    line = matcherSelector.group();
                    break;
                }
            }
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return matcher.group().substring(1, matcher.group().length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findDistribution() {
        String regexSelector = "Distributed by.*$";
        String regex = ">[A-Za-z0-9\\s.()-]+<";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find() && !read) {
                    line = matcherSelector.group();
                    read = true;
                }
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && read) {
                    return matcher.group().substring(1, matcher.group().length() - 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> findLanguage() {
        List<String> results = new ArrayList<>();
        String regexStarring = "Language.*$";
        String regex = ">[A-Za-z\\s]+<";
        Pattern patternStarring = Pattern.compile(regexStarring);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            boolean end = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherStarring = patternStarring.matcher(line);
                if (matcherStarring.find() && !read) {
                    read = true;
                    line = matcherStarring.group();
                }
                if (read) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        if (matcher.group().compareTo(">Budget<") == 0
                                || matcher.group().compareTo(">Box office<") == 0) {
                            end = true;
                            break;
                        }
                        results.add(matcher.group().substring(1, matcher.group().length() - 1));
                    }
                }
                if (end) {
                    break;
                }
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findMusic() {
        String regexSelector = "Music by.*$";
        String regex = ">[A-Za-z\\s]+<";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find() && !read) {
                    line = matcherSelector.group();
                    read = true;
                }
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && read) {
                    return matcher.group().substring(1, matcher.group().length() - 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int findBoxOffice() {
        String regexSelector = "Box office.*$";
        String regex = ">[0-9\\$A-Za-z\\s.()]+<";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader(downloadsDirectory + "currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find() && !read) {
                    line = matcherSelector.group();
                    read = true;
                }
                line = line.replace("&#160;", " ");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && read) {
                    String uncut = matcher.group().substring(2, matcher.group().length() - 1);

                    String rNum = "[0-9.]+";
                    String rMult = "\\b[a-z]+\\b";
                    Pattern pNum = Pattern.compile(rNum);
                    Pattern pMult = Pattern.compile(rMult);
                    Matcher mNum = pNum.matcher(uncut);
                    Matcher mMult = pMult.matcher(uncut);

                    double temp = 0;
                    int mult = 0;
                    if (mNum.find()) {
                        temp = Double.parseDouble(mNum.group());
                    }
                    if (mMult.find()) {
                        switch (mMult.group()) {
                            case "million": {
                                mult = 1000000;
                            }
                            break;
                            case "billion": {
                                mult = 1000000000;
                            }
                            break;
                            default: {
                                mult = 0;
                            }
                        }
                    }
                    return (int) (temp * mult);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
