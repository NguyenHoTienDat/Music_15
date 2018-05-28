package com.framgia.dattien.musicproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.model.GenreLevel;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class MusicLocalDataSource implements MusicDataSource.LocalDataSource {

    private static MusicLocalDataSource mInstance;
    private Context mContext;

    private MusicLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    public static synchronized MusicLocalDataSource getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new MusicLocalDataSource(context);
        }
        return mInstance;
    }

    @Override
    public List<Genre> makeGenes() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(mContext.getString(R.string.alternativerock_genre),
                R.drawable.rock_banner, GenreLevel.GENRE_ALTERNATIVEROCK));
        genres.add(new Genre(mContext.getString(R.string.ambient_genre),
                R.drawable.ambient_banner, GenreLevel.GENRE_AMBIENT));
        genres.add(new Genre(mContext.getString(R.string.classical_genre),
                R.drawable.classical_banner, GenreLevel.GENRE_CLASSICAL));
        genres.add(new Genre(mContext.getString(R.string.country_genre),
                R.drawable.country_banner, GenreLevel.GENRE_COUNTRY));
        genres.add(new Genre(mContext.getString(R.string.all_audio_genre),
                R.drawable.all_music_banner, GenreLevel.GENRE_ALL_AUDIO));
        return genres;
    }
}
