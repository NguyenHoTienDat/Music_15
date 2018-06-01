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
    public static final String PARAM_STREAM = "stream";
    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int READ_TIME_OUT = 5000; /*milliseconds*/
    public static final int CONNECT_TIME_OUT = 5000; /*milliseconds*/
    public static final String BREAK_LINE_CHAR = "\n";

    public static final int LIMIT = 15;
    public static final int OFFSET = 0;

    public static final int DELAY_TIME = 1000; /*milliseconds*/

    public static final String BUNDLE_MUSICS = "BUNDLE_MUSICS";
    public static final String ARGUMENT_MUSICS = "ARGUMENT_MUSICS";
    public static final String ARGUMENT_MUSIC_POSITION = "ARGUMENT_MUSIC_POSITION";

    public static final String OFFLINE_SONG_STORAGE_NAME = "ProjetcMusic";
}
