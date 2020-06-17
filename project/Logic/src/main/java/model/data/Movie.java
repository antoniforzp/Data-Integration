package model.data;

import java.util.Date;
import java.util.List;

public class Movie {

    private final String title;
    private final String cover;
    private final int year;
    private final Date releaseDate;
    private final List<String> country;
    private final String director;
    private final List<String> cast;
    private final int duration;
    private final String distribution;
    private final List<String> language;
    private final String music;
    private final int boxOffice;

    public Movie(String title, String cover, int year, Date releaseDate, List<String> country, String director, List<String> cast, int duration, String distribution, List<String> language, String music, int boxOffice) {
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

    public String getCover() {
        return cover;
    }

    public int getYear() {
        return year;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public List<String> getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getCast() {
        return cast;
    }

    public int getDuration() {
        return duration;
    }

    public String getDistribution() {
        return distribution;
    }

    public List<String> getLanguage() {
        return language;
    }

    public String getMusic() {
        return music;
    }

    public int getBoxOffice() {
        return boxOffice;
    }

    @Override
    public String toString() {

        return title + "\n" +
                "cover:\t\t" + cover + "\n" +
                "year:\t\t\t" + year + "\n" +
                "releaseDate:\t" + releaseDate + "\n" +
                "country:\t\t" + country + "\n" +
                "director:\t\t" + director + "\n" +
                "cast:\t\t\t" + cast + "\n" +
                "duration:\t\t" + duration + "\n" +
                "distribution:\t" + distribution + "\n" +
                "language:\t\t" + language + "\n" +
                "music:\t\t" + music + "\n" +
                "boxOffice:\t" + boxOffice + "\n\n";
    }
}
