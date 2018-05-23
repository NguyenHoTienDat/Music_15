package com.framgia.dattien.musicproject.data.source;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public interface MusicDataSource {

    public interface LocalDataSource {

    }

    public interface RemoteDataSource {
        void getSongsByGenre(String genre, int offset,
                             int limit, FetchDataCallback callback);

        void getSongsByFilter(String filterName, int offset,
                              int limit, FetchDataCallback callback);
    }

    public interface FetchDataCallback<T> {
        void onDataFetchSuccess(T data);

        void onDataFetchFailed(String mes);
    }
}
