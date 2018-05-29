package com.framgia.dattien.musicproject.screen.musicplay;

import android.support.annotation.IntDef;

/**
 * Created by tiendatbkhn on 27/05/2018.
 */

@IntDef({ShuffleState.SHUFFLE_NONE,ShuffleState.SHUFFLE_MIX})
public @interface ShuffleState {
    int SHUFFLE_NONE = 0;
    int SHUFFLE_MIX = 1;
}
