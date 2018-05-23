package com.framgia.dattien.musicproject.screen.main.mefragment;

import com.framgia.dattien.musicproject.screen.BaseFragment;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class MeFragment extends BaseFragment {

    public static final String ME_FRG_TAG = "MeFragment";

    private static MeFragment mNewInstance;

    public static MeFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new MeFragment();
        }
        return mNewInstance;
    }
}
