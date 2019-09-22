package com.example.movieapplication.Model;

public class Movie {
    private String id;
    private Double voteAverage;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private String releaseDate;

    public Movie(String id, String originalTitle, String posterPath, Double voteAverage, String releaseDate, String overview) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
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
