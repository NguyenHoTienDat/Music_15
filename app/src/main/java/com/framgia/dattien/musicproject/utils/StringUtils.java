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
}
