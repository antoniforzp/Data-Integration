package model.data;

import java.util.Date;
import java.util.List;

public class Movie {

    private String title;
    private String cover;
    private int year;
    private Date releaseDate;
    private List<String> country;
    private String director;
    private List<String> cast;
    private int duration;
    private String distribution;
    private List<String> language;
    private String music;
    private String boxOffice;

    public Movie(String title, String cover, int year, Date releaseDate, List<String> country, String director, List<String> cast, int duration, String distribution, List<String> language, String music, String boxOffice) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }
}
