package com.framgia.dattien.musicproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.framgia.dattien.musicproject.data.source.MusicDataSource;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class MusicLocalDataSource implements MusicDataSource.LocalDataSource {

    private static MusicLocalDataSource mInstance;

    private MusicLocalDataSource() {
    }

    public static synchronized MusicLocalDataSource getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new MusicLocalDataSource();
        }
        return mInstance;
    }
}
