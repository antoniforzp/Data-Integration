package logic;

import logic.data.Movie;
import logic.resources.HttpRequestFunctions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fetch {
    public static List<String> getAllTitles() {
        List<String> results = new ArrayList<>();
        try {
            FileReader listReader = new FileReader("list.txt");
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
        List<String> results = new ArrayList<String>();
        HttpRequestFunctions.httpRequest1("en.wikipedia.org/wiki/", search, "currentMovie.html");

        String regexB = "infobox vevent";
        String regexE = "</tbody>";
        Pattern patternB = Pattern.compile(regexB);
        Pattern patternE = Pattern.compile(regexE);

        try {
            FileReader reader = new FileReader("currentMovie.html");
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
            FileWriter writer = new FileWriter("currentMovieInfobox.html");
            for (String s : results) {
                writer.write(s + "\n");
//                System.out.println(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(findTitle());
//        System.out.println(findCover());
//        System.out.println(findProductionYear());
//        System.out.println(findReleaseUsa());
//        System.out.println(findCountry());
//        System.out.println(findDirector());
//        System.out.println(findCast());
//        System.out.println(findRuntime());
//        System.out.println(findDistribution());
//        System.out.println(findLanguage());
//        System.out.println(findMusic());
//        System.out.println(findBoxOffice());
//        System.out.println("");

        return new Movie(findTitle(), findCover(), Integer.parseInt(findProductionYear()),
                findReleaseUsa(), findCountry(), findDirector(),
                findCast(), Integer.parseInt(findRuntime()), findDistribution(),
                findLanguage(), findMusic(), findBoxOffice());
    }

    public static String findTitle() {
        String regex = ">[a-zA-Z0-9\\s&():.']+<";
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader("currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine().replace("&amp;", "&");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return matcher.group().substring(1, matcher.group().length() - 1);
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return matcher.group().substring(5);
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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

    public static String findReleaseUsa() {
        String regexSelector = "United States";
        String regex = "\\b\\d{4}-\\d{2}-\\d{2}\\b";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
                return matcher.group();
            } else {
                reader = new FileReader("currentMovieInfobox.html");
                bufferedReader = new BufferedReader(reader);
                while ((line = bufferedReader.readLine()) != null) {
                    matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        return matcher.group();
                    }
                }
            }
        } catch (IOException e) {
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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
            FileReader reader = new FileReader("currentMovieInfobox.html");
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

    public static String findBoxOffice() {
        String regexSelector = "Box office.*$";
        String regex = ">[0-9\\$A-Za-z\\s.()]+<";
        Pattern patternSelector = Pattern.compile(regexSelector);
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader("currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean read = false;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcherSelector = patternSelector.matcher(line);
                if (matcherSelector.find() && !read) {
                    line = matcherSelector.group();
                    read = true;
                }
                Matcher matcher = pattern.matcher(line.replace("&#160;", " "));
                if (matcher.find() && read) {
                    return matcher.group().substring(1, matcher.group().length() - 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
