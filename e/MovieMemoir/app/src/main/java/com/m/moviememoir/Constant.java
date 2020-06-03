package com.m.moviememoir;

public class Constant {
    public static String google_map_key = "AIzaSyAQT4LXPqjREqLuL_CKraaOq5Z-oTr7IBQ";
    private static final String APP_URL = "http://47.240.21.19:8088/MovieMemoir1/webresources/";
    public static String API_PERSON = APP_URL + "memoir.person/";
    public static String API_CREDENTIAL = APP_URL + "memoir.credential/";
    public static String API_CINEMA = APP_URL + "memoir.cinema/";
    public static String API_MEMOIR = APP_URL + "memoir.memoir/";

    public static String findByUsernameCredential = API_CREDENTIAL + "findByUsername/";
    public static String findByUsernamePerson = API_PERSON + "findByUsername/";
    public static String memoirTop5 = API_MEMOIR + "top5/";
    public static String memoirAll = API_MEMOIR + "all/";
    public static String cinemaAll = API_CINEMA + "all";
    public static String pieChart = API_MEMOIR + "piechart/%s/%s";
    public static String barChart = API_MEMOIR + "barchart/%s";


    public static String omdbapi = "http://www.omdbapi.com/?apikey=e1289c26&t=";
}
