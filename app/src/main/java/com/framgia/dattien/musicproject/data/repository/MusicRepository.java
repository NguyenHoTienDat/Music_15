package com.framgia.dattien.musicproject.data.repository;

import android.support.annotation.NonNull;
import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import com.framgia.dattien.musicproject.data.source.local.MusicLocalDataSource;
import com.framgia.dattien.musicproject.data.source.remote.MusicRemoteDataSource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class MusicRepository implements MusicDataSource.RemoteDataSource,
        MusicDataSource.LocalDataSource {

    private static MusicRepository mInstance;

    @NonNull
    private MusicLocalDataSource mMusicLocalDataSource;

    @NonNull
    private MusicRemoteDataSource mMusicRemoteDataSource;

    private MusicRepository(@NonNull MusicLocalDataSource musicLocalDataSource,
                            @NonNull MusicRemoteDataSource musicRemoteDataSource) {
        mMusicLocalDataSource = musicLocalDataSource;
        mMusicRemoteDataSource = musicRemoteDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param remoteDataSource the backend data source
     * @param localDataSource  the device storage data source
     * @return the {@link MusicRepository} instance
     */
    public static synchronized MusicRepository getInstance(
            @NonNull MusicLocalDataSource localDataSource,
            @NonNull MusicRemoteDataSource remoteDataSource) {
        if (mInstance == null) {
            mInstance = new MusicRepository(checkNotNull(localDataSource),
                    checkNotNull(remoteDataSource));
        }
        return mInstance;
    }

    @Override
    public void getSongsByGenre(String genre, int offset,
                                int limit, MusicDataSource.FetchDataCallback callback) {
        mMusicRemoteDataSource.getSongsByGenre(genre, offset, limit, callback);
    }

    @Override
    public void getSongsByFilter(String filterName, int offset,
                                 int limit, MusicDataSource.FetchDataCallback callback) {
        mMusicRemoteDataSource.getSongsByFilter(filterName, offset, limit, callback);
    }

    @Override
    public List<Genre> makeGenes() {
        return mMusicLocalDataSource.makeGenes();
    }
}
