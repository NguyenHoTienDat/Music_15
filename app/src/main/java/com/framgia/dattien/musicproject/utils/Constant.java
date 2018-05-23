package com.framgia.dattien.musicproject.utils;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public final class Constant {

    /**
     * Http connection
     */

    public static final String BASE_GENRE_URL = "https://api-v2.soundcloud.com/";
    public static final String BASE_SEARCH_URL = "https://api.soundcloud.com/";
    public static final String PARAM_MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    public static final String PARAM_FILTER_MODE = "tracks?filter=public";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_OFFSET = "offset";
    public static final String PARAM_SEARCH = "q";
    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int READ_TIME_OUT = 5000; /*milliseconds*/
    public static final int CONNECT_TIME_OUT = 5000; /*milliseconds*/
    public static final String BREAK_LINE_CHAR = "\n";

    /**
     * Tab postion in MainActivity
     */

    public static final int TAB_HOME_POSITION = 0;
    public static final int TAB_ME_POSITION = 1;
    public static final int TAB_INFORMATION_POSITION = 2;

}
