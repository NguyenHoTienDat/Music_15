package com.framgia.dattien.musicproject.screen.musicplay;

import android.support.annotation.IntDef;

/**
 * Created by tiendatbkhn on 26/05/2018.
 */

@IntDef({MediaState.PLAYING, MediaState.PAUSE, MediaState.STOP,
        MediaState.PREPARE, MediaState.ERROR_STREAM,})
public @interface MediaState {
    int PLAYING = 0;
    int PAUSE = 1;
    int STOP = 2;
    int PREPARE = 3;
    int ERROR_STREAM = 4;
}
