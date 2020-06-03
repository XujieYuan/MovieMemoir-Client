package com.m.moviememoir.Bean;

public class Memoir {

    /**
     * cid : 1
     * comment : Good Chinese movie
     * memoirid : 11
     * moviename : Youth
     * moviereleasedate : Fri, 15 Dec 2017 00:00:00 GMT
     * pid : 1
     * ratingscore : 98
     * watchdate : Fri, 22 Dec 2017 00:00:00 GMT
     */

    private int cid;
    private String comment;
    private int memoirid;
    private String moviename;
    private String moviereleasedate;
    private int pid;
    private int ratingscore;
    private String watchdate;
    private Movie movie;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMemoirid() {
        return memoirid;
    }

    public void setMemoirid(int memoirid) {
        this.memoirid = memoirid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(String moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(int ratingscore) {
        this.ratingscore = ratingscore;
    }

    public String getWatchdate() {
        return watchdate;
    }

    public void setWatchdate(String watchdate) {
        this.watchdate = watchdate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
