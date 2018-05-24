package com.framgia.dattien.musicproject.data.source.remote.config;

import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.data.model.User;
import com.framgia.dattien.musicproject.data.model.SongResponse;
import com.framgia.dattien.musicproject.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public class MusicDataHandle {

    public SongResponse getSongsByGenre(JSONObject jsonObject) throws JSONException {
        SongResponse result = new SongResponse();
        result.setGenre(jsonObject.optString(SongResponse.SongResponseEntry.WRAPPER_GENRE));
        result.setKind(jsonObject.optString(SongResponse.SongResponseEntry.WRAPPER_KIND));
        result.setNextHref(jsonObject.optString(SongResponse.SongResponseEntry.WRAPPER_NEXT_HREF));
        result.setQueryUrn(jsonObject.optString(SongResponse.SongResponseEntry.WRAPPER_QUERY_URN));

        List<Song> songs = new ArrayList<>();
        JSONArray jsonCollectionArray =
                jsonObject.getJSONArray(SongResponse.SongResponseEntry.WRAPPER_SONGS);
        for (int i = 0; i < jsonCollectionArray.length(); i++) {
            JSONObject jsonSongObject = jsonCollectionArray.getJSONObject(i).getJSONObject(Song.SongEntry.SONG_TRACK);
            Song song = parseSongObject(jsonSongObject);
            if (song != null) {
                songs.add(song);
            }
        }
        result.setSongs(songs);
        return result;
    }

    public List<Song> getSongsByFilter(JSONArray jsonArray) throws JSONException {
        List<Song> songs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonSong = jsonArray.getJSONObject(i);
            Song song = parseSongObject(jsonSong);
            if (song != null) {
                songs.add(song);
            }
        }
        return songs;
    }

    protected Song parseSongObject(JSONObject jsonObject) {

        Song song = null;
        try {
            JSONObject jsonUserObject = jsonObject.getJSONObject(Song.SongEntry.SONG_USER);
            User user = new User();
            user.setAvatarUrl(jsonUserObject.optString(User.UserEntry.USER_AVATR_URL));
            //user.setFullName(checkNotNull(jsonUserObject.optString("full_name")));
            user.setUserName(jsonUserObject.optString(User.UserEntry.USER_USERNAME));

            song = new Song.SongBuilder()
                    .setId(jsonObject.optInt(Song.SongEntry.SONG_ID))
                    .setArtworkUrl(jsonObject.optString(Song.SongEntry.SONG_ARTWORK_URL))
                    .setDescription(jsonObject.optString(Song.SongEntry.SONG_DESCRIPTION))
                    .setDownloadable(jsonObject.optBoolean(Song.SongEntry.SONG_DOWNLOADABLE))
                    .setDownloadCount(jsonObject.optInt(Song.SongEntry.SONG_DOWNLOAD_COUNT))
                    .setDownloadUrl(jsonObject.optString(Song.SongEntry.SONG_DOWNLOAD_URL))
                    .setDuration(jsonObject.optInt(Song.SongEntry.SONG_DURATION))
                    .setLikesCount(jsonObject.optInt(Song.SongEntry.SONG_LIKES_COUNT))
                    .setPlaybackCount(jsonObject.optInt(Song.SongEntry.SONG_PLAYBACK_COUNT))
                    .setTitle(jsonObject.optString(Song.SongEntry.SONG_TITLE))
                    .setUri(jsonObject.optString(Song.SongEntry.SONG_URI))
                    .setUser(user).build();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return song;
    }

    protected String getJSONStringFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constant.REQUEST_METHOD_GET);
        httpURLConnection.setReadTimeout(Constant.READ_TIME_OUT);
        httpURLConnection.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constant.BREAK_LINE_CHAR);
        }
        br.close();
        httpURLConnection.disconnect();
        return sb.toString();
    }
}
