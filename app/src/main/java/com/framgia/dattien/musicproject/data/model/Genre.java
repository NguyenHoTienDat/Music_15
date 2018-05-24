package com.framgia.dattien.musicproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

public class Genre implements Parcelable{
    private String mGenreName;
    private int mGenreAvatar;

    public Genre(String genreName, int genreAvatar) {
        mGenreName = genreName;
        mGenreAvatar = genreAvatar;
    }

    protected Genre(Parcel in) {
        mGenreName = in.readString();
        mGenreAvatar = in.readInt();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGenreName);
        dest.writeInt(mGenreAvatar);
    }

    public String getGenreName() {
        return mGenreName;
    }

    public void setGenreName(String genreName) {
        mGenreName = genreName;
    }

    public int getGenreAvatar() {
        return mGenreAvatar;
    }

    public void setGenreAvatar(int genreAvatar) {
        mGenreAvatar = genreAvatar;
    }
}
