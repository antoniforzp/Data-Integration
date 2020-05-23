import resources.HttpRequestFunctions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fetch {
    public static void findAllMovies () {
        try {
            FileReader listReader = new FileReader("list.txt");
            BufferedReader listBufferedReader = new BufferedReader(listReader);
            String listMovie;
            while((listMovie = listBufferedReader.readLine()) != null) {
                findMovie(listMovie);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Movie findMovie (String search) {
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
            while((line = bufferedReader.readLine()) != null) {
                Matcher matcherB = patternB.matcher(line);
                Matcher matcherE = patternE.matcher(line);
                if(matcherB.find()) {
                    read = true;
                }
                if(read)
                    results.add(line);
                if( read && matcherE.find()) {
                    break;
                }
            }
            FileWriter writer = new FileWriter("currentMovieInfobox.html");
            for(String s: results) {
                writer.write(s + "\n");
//                System.out.println(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(findTitle());
        System.out.println(findCover());
        System.out.println("");
        //getAuthor
        //etc
        //return new Movie

        return null;
    }

    public static String findTitle() {
        String regex = ">[a-zA-Z0-9\\s&():.']+<";
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader("currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine().replace("&amp;", "&");
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()) {
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
            if(matcher.find()) {
                return matcher.group().substring(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String findProductionYear() {
        String regex = "src=\"\\S*\\.jpg";
        Pattern pattern = Pattern.compile(regex);
        try {
            FileReader reader = new FileReader("currentMovieInfobox.html");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()) {
                return matcher.group().substring(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
