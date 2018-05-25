package com.framgia.dattien.musicproject.screen.main.mefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.dattien.musicproject.R;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }
}
