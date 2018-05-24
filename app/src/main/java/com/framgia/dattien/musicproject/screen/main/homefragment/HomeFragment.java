package com.framgia.dattien.musicproject.screen.main.homefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.data.source.local.MusicLocalDataSource;
import com.framgia.dattien.musicproject.data.source.remote.MusicRemoteDataSource;
import com.framgia.dattien.musicproject.screen.BaseFragment;
import com.framgia.dattien.musicproject.screen.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class HomeFragment extends BaseFragment
        implements HomeContract.View, BaseRecyclerViewAdapter.OnItemClickListener<Genre> {

    public static final String HOME_FRG_TAG = "HomeFragment";

    private static HomeFragment mNewInstance;
    private HomeContract.Presenter mPresenter;
    private GenreAdapter mGenreAdapter;

    private RecyclerView mRecyclerViewGenre;

    public static HomeFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new HomeFragment();
        }
        return mNewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicRepository musicRepository =
                MusicRepository.getInstance(MusicLocalDataSource.getInstance(getActivity()),
                        MusicRemoteDataSource.getInstance(getActivity()));
        mPresenter = new HomePresenter(musicRepository);
        mPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        initComponents();
        mPresenter.onStart();
    }

    @Override
    public void updateGenres(List<Genre> genres) {
        checkNotNull(mGenreAdapter).updateData(genres);
    }

    public void bindView() {
        mRecyclerViewGenre = getView().findViewById(R.id.rv_genre_music);
    }

    public void initComponents() {
        mGenreAdapter = new GenreAdapter(getContext(),
                new ArrayList<Genre>(), this);
        mRecyclerViewGenre.setAdapter(mGenreAdapter);
    }

    @Override
    public void onItemClick(View view, Genre data, int position) {

    }
}
