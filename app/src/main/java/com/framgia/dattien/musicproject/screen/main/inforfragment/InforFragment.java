package com.framgia.dattien.musicproject.screen.main.inforfragment;

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

public class InforFragment extends BaseFragment {

    public static final String INFOR_FRG_TAG = "InforFragment";

    private static InforFragment mNewInstance;

    public static InforFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new InforFragment();
        }
        return mNewInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infor, container, false);
    }
}
