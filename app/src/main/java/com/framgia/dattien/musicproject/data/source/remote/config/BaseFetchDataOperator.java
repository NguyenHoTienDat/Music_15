package com.framgia.dattien.musicproject.data.source.remote.config;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.framgia.dattien.musicproject.data.model.SongResponse;
import com.framgia.dattien.musicproject.data.source.MusicDataSource;
import com.framgia.dattien.musicproject.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public abstract class BaseFetchDataOperator<T>
        extends AsyncTask<String, Void, BaseFetchDataOperator.WrapperData<T>> {

    protected MusicDataHandle mMusicDataHandle;
    protected MusicDataSource.FetchDataCallback<T> mCallback;
    private WrapperData<T> mWrapperData;

    protected abstract T parseData(String data) throws JSONException;

    public BaseFetchDataOperator(MusicDataSource.FetchDataCallback<T> callback) {
        mCallback = callback;
        mMusicDataHandle = new MusicDataHandle();
        mWrapperData = new WrapperData<>();
    }

    @Override
    protected WrapperData<T> doInBackground(String... strings) {
        try {
            String jsonString = mMusicDataHandle.getJSONStringFromURL(strings[0]);
            mWrapperData.setData(parseData(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
            mWrapperData.setException(e);
        } catch (IOException e) {
            e.printStackTrace();
            mWrapperData.setException(e);
        }
        return mWrapperData;
    }

    @Override
    protected void onPostExecute(WrapperData<T> data) {
        super.onPostExecute(data);
        if (mWrapperData.getException() == null) {
            checkNotNull(mCallback).onDataFetchSuccess(data.getData());
            return;
        }

        if (mWrapperData.getException() != null) {
            checkNotNull(mCallback).onDataFetchFailed(data.getException().getMessage());
        }

    }

    public static class WrapperData<T> {
        private T mData;
        private Exception mException;

        public T getData() {
            return mData;
        }

        public void setData(T data) {
            mData = data;
        }

        public Exception getException() {
            return mException;
        }

        public void setException(Exception exception) {
            mException = exception;
        }
    }

}
