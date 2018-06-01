package com.framgia.dattien.musicproject.data.source;

import com.framgia.dattien.musicproject.data.model.Genre;
import java.util.List;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public interface MusicDataSource {

    interface LocalDataSource {
        List<Genre> makeGenes();
    }

    interface RemoteDataSource {
        void getSongsByGenre(String genre, int offset,
                             int limit, FetchDataCallback callback);

        void getSongsByFilter(String filterName, int offset,
                              int limit, FetchDataCallback callback);

        void getDataLoadMore(String hrefNext, FetchDataCallback callback);
    }

    interface FetchDataCallback<T> {
        void onDataFetchSuccess(T data);

        void onDataFetchFailed(String mes);
    }
}
