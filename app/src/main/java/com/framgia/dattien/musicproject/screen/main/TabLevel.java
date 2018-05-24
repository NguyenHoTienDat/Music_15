package com.framgia.dattien.musicproject.screen.main;

import android.support.annotation.IntDef;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

@IntDef({TabLevel.TAB_HOME, TabLevel.TAB_ME, TabLevel.TAB_INFOR})
public @interface TabLevel {
    int TAB_HOME = 0;
    int TAB_ME = 1;
    int TAB_INFOR = 2;
}
