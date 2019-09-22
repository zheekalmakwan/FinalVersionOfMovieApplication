package com.example.movieapplication.DataBase;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private int movieID;
    private Float voteAverage;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private String releaseDate;

    @Ignore
    public MovieEntry(int movieID, Float voteAverage, String posterPath, String originalTitle, String overview, String releaseDate) {
        this.movieID = movieID;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public MovieEntry(int ID, int movieID, Float voteAverage, String posterPath, String originalTitle, String overview, String releaseDate) {
        this.ID = ID;
        this.movieID = movieID;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
