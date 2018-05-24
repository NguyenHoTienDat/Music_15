package com.framgia.dattien.musicproject.screen.main.homefragment;

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

public class HomeFragment extends BaseFragment {

    public static final String HOME_FRG_TAG = "HomeFragment";

    private static HomeFragment mNewInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new HomeFragment();
        }
        return mNewInstance;
    }
}
