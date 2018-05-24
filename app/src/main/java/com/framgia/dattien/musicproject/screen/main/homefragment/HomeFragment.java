package com.framgia.dattien.musicproject.screen.main.homefragment;

import com.framgia.dattien.musicproject.screen.BaseFragment;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class HomeFragment extends BaseFragment {

    public static final String HOME_FRG_TAG = "HomeFragment";

    private static HomeFragment mNewInstance;

    public static HomeFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new HomeFragment();
        }
        return mNewInstance;
    }
}
