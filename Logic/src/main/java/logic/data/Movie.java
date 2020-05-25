package logic.data;

import java.util.List;

public class Movie {
    private String title;
    private String cover;
    private int year;
    private String releaseDate;
    private List<String> country;
    private String director;
    private List<String> cast;
    private int duration;
    private String distribution;
    private List<String> language;
    private String music;
    private String boxOffice;

    public Movie(String title, String cover, int year, String releaseDate, List<String> country, String director, List<String> cast, int duration, String distribution, List<String> language, String music, String boxOffice) {
        this.title = title;
        this.cover = cover;
        this.year = year;
        this.releaseDate = releaseDate;
        this.country = country;
        this.director = director;
        this.cast = cast;
        this.duration = duration;
        this.distribution = distribution;
        this.language = language;
        this.music = music;
        this.boxOffice = boxOffice;
    }

    public String getTitle() {
        return title;
    }

//
//    DECIDE ON CLASS STRUCTURE
//
}
