package com.framgia.dattien.musicproject.screen.main.inforfragment;

import com.framgia.dattien.musicproject.screen.BaseFragment;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class InforFragment extends BaseFragment {

    public static final String INFOR_FRG_TAG = "InforFragment";

    private static InforFragment mNewInstance;

    public static InforFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new InforFragment();
        }
        return mNewInstance;
    }
}
