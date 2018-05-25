package com.framgia.dattien.musicproject.screen.main.homefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.data.source.local.MusicLocalDataSource;
import com.framgia.dattien.musicproject.data.source.remote.MusicRemoteDataSource;
import com.framgia.dattien.musicproject.screen.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 23/05/2018.
 */

public class HomeFragment extends BaseFragment
        implements HomeContract.View, SongAdapter.OnSongItemClickListener,
        GenreAdapter.OnGenreItemClickListener {

    public static final String HOME_FRG_TAG = "HomeFragment";

    private static HomeFragment mNewInstance;
    private HomeContract.Presenter mPresenter;
    private GenreAdapter mGenreAdapter;
    private SongAdapter mSongAdapter;

    private RecyclerView mRecyclerViewGenre;
    private RecyclerView mRecyclerViewSong;

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
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        bindView(view);
        initComponents();
        mPresenter.onStart();
        return view;
    }

    @Override
    public void updateGenres(List<Genre> genres) {
        checkNotNull(mGenreAdapter).updateData(genres);
    }

    @Override
    public void updateHotSongs(List<Song> songs) {
        checkNotNull(mSongAdapter).updateData(songs);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void bindView(View view) {
        mRecyclerViewGenre = view.findViewById(R.id.rv_genre_music);
        mRecyclerViewSong = view.findViewById(R.id.rv_hot_music);
    }

    public void initComponents() {
        mGenreAdapter = new GenreAdapter(getContext(),
                new ArrayList<Genre>(), this);
        mRecyclerViewGenre.setAdapter(mGenreAdapter);

        mSongAdapter = new SongAdapter(getContext(),
                new ArrayList<Song>(), this);
        mRecyclerViewSong.setAdapter(mSongAdapter);
        mRecyclerViewSong.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemGenreClick(View v, Genre genre, int position) {

    }

    @Override
    public void onItemSongClick(View v, Song song, int position) {

    }
}
