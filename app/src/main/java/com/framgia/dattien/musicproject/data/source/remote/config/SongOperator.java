package com.framgia.dattien.musicproject.data.source.remote.config;

import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class SongOperator extends BaseFetchDataOperator<List<Song>> {
    public SongOperator(MusicDataSource.FetchDataCallback<List<Song>> callback) {
        super(callback);
    }

    @Override
    protected List<Song> parseData(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        return mMusicDataHandle.getSongsByFilter(jsonArray);
    }
}
