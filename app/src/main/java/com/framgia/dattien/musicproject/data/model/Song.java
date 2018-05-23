package com.framgia.dattien.musicproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class Song implements Parcelable {

    private int mId;
    private String mArtworkUrl;
    private String mDescription;
    private String mDownloadUrl;
    private int mDuration;
    private boolean mDownloadable;
    private String mTitle;
    private String mUri;
    private int mDownloadCount;
    private int mLikesCount;
    private int mPlaybackCount;
    private User mUser;

    public Song() {
    }

    protected Song(Parcel in) {
        mId = in.readInt();
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        mDownloadUrl = in.readString();
        mDuration = in.readInt();
        mDownloadable = in.readByte() != 0;
        mTitle = in.readString();
        mUri = in.readString();
        mDownloadCount = in.readInt();
        mLikesCount = in.readInt();
        mPlaybackCount = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public int getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        mDownloadCount = downloadCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mArtworkUrl);
        dest.writeString(mDescription);
        dest.writeString(mDownloadUrl);
        dest.writeInt(mDuration);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeInt(mDownloadCount);
        dest.writeInt(mLikesCount);
        dest.writeInt(mPlaybackCount);
    }

    public static class SongEntry {
        public static final String SONG_ID = "id";
        public static final String SONG_ARTWORK_URL = "artwork_url";
        public static final String SONG_DESCRIPTION = "description";
        public static final String SONG_DOWNLOAD_URL = "download_url";
        public static final String SONG_DURATION = "duration";
        public static final String SONG_DOWNLOADABLE = "downloadable";
        public static final String SONG_URI = "uri";
        public static final String SONG_TITLE = "title";
        public static final String SONG_DOWNLOAD_COUNT = "download_count";
        public static final String SONG_LIKES_COUNT = "likes_count";
        public static final String SONG_PLAYBACK_COUNT = "playback_count";
        public static final String SONG_USER = "user";
        public static final String SONG_TRACK = "track";
    }

    public static class SongBuilder {

        private Song mSong;

        public SongBuilder() {
            mSong = new Song();
        }

        public SongBuilder setId(int id) {
            mSong.setId(id);
            return this;
        }

        public SongBuilder setArtworkUrl(String artworkUrl) {
            mSong.setArtworkUrl(artworkUrl);
            return this;
        }

        public SongBuilder setDescription(String description) {
            mSong.setDescription(description);
            return this;
        }

        public SongBuilder setDownloadUrl(String downloadUrl) {
            mSong.setDownloadUrl(downloadUrl);
            return this;
        }

        public SongBuilder setDuration(int duration) {
            mSong.setDuration(duration);
            return this;
        }

        public SongBuilder setDownloadable(boolean downloadable) {
            mSong.setDownloadable(downloadable);
            return this;
        }

        public SongBuilder setTitle(String title) {
            mSong.setTitle(title);
            return this;
        }

        public SongBuilder setUri(String uri) {
            mSong.setUri(uri);
            return this;
        }

        public SongBuilder setDownloadCount(int downloadCount) {
            mSong.setDownloadCount(downloadCount);
            return this;
        }

        public SongBuilder setLikesCount(int likesCount) {
            mSong.setLikesCount(likesCount);
            return this;
        }

        public SongBuilder setPlaybackCount(int playbackCount) {
            mSong.setPlaybackCount(playbackCount);
            return this;
        }

        public SongBuilder setUser(User user) {
            mSong.setUser(user);
            return this;
        }

        public Song build() {
            return mSong;
        }
    }
}
