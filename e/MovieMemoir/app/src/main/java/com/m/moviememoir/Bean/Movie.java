package com.m.moviememoir.Bean;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    /**
     * Title : 2012
     * Year : 2009
     * Rated : PG-13
     * Released : 13 Nov 2009
     * Runtime : 158 min
     * Genre : Action, Adventure, Sci-Fi
     * Director : Roland Emmerich
     * Writer : Roland Emmerich, Harald Kloser
     * Actors : John Cusack, Amanda Peet, Chiwetel Ejiofor, Thandie Newton
     * Plot : A frustrated writer struggles to keep his family alive when a series of global catastrophes threatens to annihilate mankind.
     * Language : English, French, Tibetan, Mandarin, Russian, Hindi, Portuguese, Latin, Italian, Spanish
     * Country : USA
     * Awards : 5 wins & 21 nominations.
     * Poster : https://m.media-amazon.com/images/M/MV5BMTY0MjEyODQzMF5BMl5BanBnXkFtZTcwMTczMjQ4Mg@@._V1_SX300.jpg
     * Ratings : [{"Source":"Internet Movie Database","Value":"5.8/10"},{"Source":"Rotten Tomatoes","Value":"39%"},{"Source":"Metacritic","Value":"49/100"}]
     * Metascore : 49
     * imdbRating : 5.8
     * imdbVotes : 343,736
     * imdbID : tt1190080
     * Type : movie
     * DVD : N/A
     * BoxOffice : N/A
     * Production : N/A
     * Website : N/A
     * Response : True
     */

    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;
    private String DVD;
    private String BoxOffice;
    private String Production;
    private String Website;
    private String Response;
    private List<RatingsBean> Ratings;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String Rated) {
        this.Rated = Rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String Released) {
        this.Released = Released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String Runtime) {
        this.Runtime = Runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String Director) {
        this.Director = Director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String Writer) {
        this.Writer = Writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String Actors) {
        this.Actors = Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String Plot) {
        this.Plot = Plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String Awards) {
        this.Awards = Awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String Metascore) {
        this.Metascore = Metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getDVD() {
        return DVD;
    }

    public void setDVD(String DVD) {
        this.DVD = DVD;
    }

    public String getBoxOffice() {
        return BoxOffice;
    }

    public void setBoxOffice(String BoxOffice) {
        this.BoxOffice = BoxOffice;
    }

    public String getProduction() {
        return Production;
    }

    public void setProduction(String Production) {
        this.Production = Production;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    public List<RatingsBean> getRatings() {
        return Ratings;
    }

    public void setRatings(List<RatingsBean> Ratings) {
        this.Ratings = Ratings;
    }

    public static class RatingsBean implements Serializable{
        /**
         * Source : Internet Movie Database
         * Value : 5.8/10
         */

        private String Source;
        private String Value;

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }
    }
}
