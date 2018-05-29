package com.framgia.dattien.musicproject.screen.main.musicplay;

import android.support.annotation.IntDef;

/**
 * Created by tiendatbkhn on 26/05/2018.
 */

@IntDef({MediaState.MEDIA_PLAYING, MediaState.MEDIA_PAUSE, MediaState.MEDIA_STOP,
        MediaState.MEDIA_PREPARE, MediaState.MEDIA_ERROR_STREAM,})
public @interface MediaState {
    int MEDIA_PLAYING = 0;
    int MEDIA_PAUSE = 1;
    int MEDIA_STOP = 2;
    int MEDIA_PREPARE = 3;
    int MEDIA_ERROR_STREAM = 4;
}
