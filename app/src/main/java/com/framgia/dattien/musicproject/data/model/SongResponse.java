package com.framgia.dattien.musicproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class SongResponse implements Parcelable {

    private String mGenre;
    private String mKind;
    private List<Song> mSongs;
    private String mQueryUrn;
    private String mNextHref;

    public SongResponse() {
    }

    protected SongResponse(Parcel in) {
        mGenre = in.readString();
        mKind = in.readString();
        mSongs = in.createTypedArrayList(Song.CREATOR);
        mQueryUrn = in.readString();
        mNextHref = in.readString();
    }

    public static final Creator<SongResponse> CREATOR = new Creator<SongResponse>() {
        @Override
        public SongResponse createFromParcel(Parcel in) {
            return new SongResponse(in);
        }

        @Override
        public SongResponse[] newArray(int size) {
            return new SongResponse[size];
        }
    };

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

    public String getQueryUrn() {
        return mQueryUrn;
    }

    public void setQueryUrn(String queryUrn) {
        mQueryUrn = queryUrn;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGenre);
        dest.writeString(mKind);
        dest.writeTypedList(mSongs);
        dest.writeString(mQueryUrn);
        dest.writeString(mNextHref);
    }

    public static class SongResponseEntry {
        public static final String WRAPPER_GENRE = "genre";
        public static final String WRAPPER_KIND = "kind";
        public static final String WRAPPER_SONGS = "collection";
        public static final String WRAPPER_QUERY_URN = "query_urn";
        public static final String WRAPPER_NEXT_HREF = "next_href";
    }
}
