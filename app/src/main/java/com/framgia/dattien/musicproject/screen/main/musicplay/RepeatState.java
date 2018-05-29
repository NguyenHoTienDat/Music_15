package com.framgia.dattien.musicproject.screen.main.musicplay;

import android.support.annotation.IntDef;

/**
 * Created by tiendatbkhn on 27/05/2018.
 */

@IntDef({RepeatState.REPEAT_NONE, RepeatState.REPEAT_ONE, RepeatState.REPEAT_ALL})
public @interface RepeatState {
    int REPEAT_NONE = 0;
    int REPEAT_ONE = 1;
    int REPEAT_ALL = 2;
}
