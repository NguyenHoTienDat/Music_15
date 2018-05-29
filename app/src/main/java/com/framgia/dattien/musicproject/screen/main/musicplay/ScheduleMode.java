package com.framgia.dattien.musicproject.screen.main.musicplay;

import android.support.annotation.IntDef;

/**
 * Created by tiendatbkhn on 28/05/2018.
 */

@IntDef({ScheduleMode.SCHEDULE_CANCEL, ScheduleMode.SCHEDULE_TEN_SECONDS,
        ScheduleMode.SCHEDULE_TWENTY_SECONDS, ScheduleMode.SCHEDULE_ONE_MINUTE,
        ScheduleMode.SCHEDULE_TWO_MINUTES})
public @interface ScheduleMode {
    int SCHEDULE_CANCEL = -1;
    int SCHEDULE_TEN_SECONDS = 10000;
    int SCHEDULE_TWENTY_SECONDS = 20000;
    int SCHEDULE_ONE_MINUTE = 60000;
    int SCHEDULE_TWO_MINUTES = 120000;
}
