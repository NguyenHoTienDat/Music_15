package com.framgia.dattien.musicproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class User implements Parcelable {

    private String mAvatarUrl;
    private String mFullName;
    private String mUserName;

    public User() {
    }

    protected User(Parcel in) {
        mAvatarUrl = in.readString();
        mFullName = in.readString();
        mUserName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAvatarUrl);
        dest.writeString(mFullName);
        dest.writeString(mUserName);
    }

    public static class UserEntry {
        public static final String USER_AVATR_URL= "avatar_url";
        public static final String USER_FULL_NAME= "full_name";
        public static final String USER_USERNAME= "username";
    }
}
