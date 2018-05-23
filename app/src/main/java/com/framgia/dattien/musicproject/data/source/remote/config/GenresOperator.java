package com.framgia.dattien.musicproject.data.source.remote.config;

import com.framgia.dattien.musicproject.data.model.SongResponse;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class GenresOperator extends BaseFetchDataOperator<SongResponse> {
    public GenresOperator(MusicDataSource.FetchDataCallback<SongResponse> callback) {
        super(callback);
    }

    @Override
    public SongResponse parseData(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        return  mMusicDataHandle.getSongsByGenre(jsonObject);
    }
}
