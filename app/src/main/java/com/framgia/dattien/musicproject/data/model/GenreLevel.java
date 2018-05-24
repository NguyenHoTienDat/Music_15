package com.framgia.dattien.musicproject.data.model;

import android.support.annotation.StringDef;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

@StringDef({GenreLevel.GENRE_ALL_MUSIC, GenreLevel.GENRE_ALL_AUDIO,
        GenreLevel.GENRE_ALTERNATIVEROCK, GenreLevel.GENRE_AMBIENT,
        GenreLevel.GENRE_CLASSICAL, GenreLevel.GENRE_COUNTRY})
public @interface GenreLevel {
    String GENRE_ALL_MUSIC = "all_music";
    String GENRE_ALL_AUDIO = "all_audio";
    String GENRE_ALTERNATIVEROCK = "alternativerock";
    String GENRE_AMBIENT = "ambient";
    String GENRE_CLASSICAL = "classical";
    String GENRE_COUNTRY = "country";
}
