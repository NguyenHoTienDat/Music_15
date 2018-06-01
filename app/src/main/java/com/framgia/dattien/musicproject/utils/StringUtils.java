package com.framgia.dattien.musicproject.utils;

import com.framgia.dattien.musicproject.BuildConfig;

/**
 * Created by tiendatbkhn on 22/05/2018.
 */

public final class StringUtils {
    public static String getUriConvert(String baseUri) {
        return String.format("%s/%s?%s=%s", baseUri, Constant.PARAM_STREAM,
                Constant.PARAM_CLIENT_ID, BuildConfig.API_KEY);
    }

    public static String convertMediaTime(long milliseconds) {
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        return hours > 0 ? String.format("%02d:%02d:%02d",
                hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public static String getUriLoadMoreConvert(String hrefNext) {
        return String.format("%s&%s=%s",hrefNext,Constant.PARAM_CLIENT_ID,BuildConfig.API_KEY);
    }

    public static String getUriDownloadConvert(String originUri) {
        return String.format("%s?%s=%s", originUri, Constant.PARAM_CLIENT_ID, BuildConfig.API_KEY);
    }

    /**
     * Song name save in storage in format : songname_songid
     * This method get songname from that string to show on UI
     * @param generalSongName
     * @return
     */
    public static String getSongNameOfflineConvert(String generalSongName) {
        return generalSongName.substring(0,
                generalSongName.lastIndexOf("_")
        );
    }

    /**
     * Song name save in storage in format : songname_songid
     * This method get songid for query exist in storage
     * @param generalSongName
     * @return
     */
    public static String getSongIdOfflineConvert(String generalSongName) {
        return generalSongName.substring(
                generalSongName.lastIndexOf("_") + 1,
                generalSongName.lastIndexOf(".")
        );
    }
}
