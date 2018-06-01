package com.framgia.dattien.musicproject.utils;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

import com.framgia.dattien.musicproject.data.model.Song;

/**
 * Created by tiendatbkhn on 01/06/2018.
 */

public final class DownloadUtils {
    public static long downloadSong(DownloadManager downloadManager, Song song) {
        Uri downloadUri =
                Uri.parse(StringUtils.getUriDownloadConvert(song.getDownloadUrl()));
        // Create request for android download manager
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        //Setting title of request
        request.setTitle("Download Song");

        //Setting description of request
        request.setDescription(song.getTitle());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS+"/"+Constant.OFFLINE_SONG_STORAGE_NAME,
                song.getTitle()+"_"+song.getId()+".mp3");

        //Enqueue download and save into referenceId
        return downloadManager.enqueue(request);
    }
}
