package com.framgia.dattien.musicproject.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.framgia.dattien.musicproject.BuildConfig;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import com.framgia.dattien.musicproject.data.source.remote.config.GenresOperator;
import com.framgia.dattien.musicproject.data.source.remote.config.SongOperator;
import com.framgia.dattien.musicproject.utils.Constant;
import com.framgia.dattien.musicproject.utils.StringUtils;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class MusicRemoteDataSource implements MusicDataSource.RemoteDataSource {

    private static MusicRemoteDataSource mInstance;

    private MusicRemoteDataSource() {
    }

    public static synchronized MusicRemoteDataSource getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new MusicRemoteDataSource();
        }
        return mInstance;
    }

    @Override
    public void getSongsByGenre(String genre, int offset,
                                int limit, MusicDataSource.FetchDataCallback callback) {
        String requestUrl = String.format("%s%s%s&%s=%s&%s=%d&%s=%d", Constant.BASE_GENRE_URL,
                Constant.PARAM_MUSIC_GENRE, genre, Constant.PARAM_CLIENT_ID,
                BuildConfig.API_KEY, Constant.PARAM_LIMIT, limit, Constant.PARAM_OFFSET, offset);

        new GenresOperator(callback).execute(requestUrl);
    }

    @Override
    public void getSongsByFilter(String filterName, int offset,
                                 int limit, MusicDataSource.FetchDataCallback callback) {
        String requestUrl = String.format("%s%s&%s=%s&%s=%s&%s=%d&%s=%d", Constant.BASE_SEARCH_URL,
                Constant.PARAM_FILTER_MODE, Constant.PARAM_CLIENT_ID,
                BuildConfig.API_KEY, Constant.PARAM_SEARCH, filterName,
                Constant.PARAM_LIMIT, limit, Constant.PARAM_OFFSET, offset);

        new SongOperator(callback).execute(requestUrl);
    }

    @Override
    public void getDataLoadMore(String hrefNext, MusicDataSource.FetchDataCallback callback) {
        String requestUrl = StringUtils.getUriLoadMoreConvert(hrefNext);
        new GenresOperator(callback).execute(requestUrl);
    }
}
