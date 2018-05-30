package com.framgia.dattien.musicproject.screen.musicplay.songlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.BaseFragment;
import com.framgia.dattien.musicproject.screen.genredetails.SongDetailsAdapter;
import com.framgia.dattien.musicproject.utils.Constant;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 29/05/2018.
 */

public class SongsFragment extends BaseFragment
        implements SongDetailsAdapter.OnSongDetailsItemClickListener {
    public static final String SONGS_FRAGMENT_TAG = "SONGS_FRAGMENT_TAG";

    private static SongsFragment mNewInstance;

    private OnSongsFragmentListener mOnSongsFragmentListener;
    private RecyclerView mRecyclerViewSongs;
    private LinearLayoutManager mLayoutManager;
    private SongDetailsAdapter mSongAdapter;
    private List<Song> mSongs;
    private int mCurrentSongPosition;
    private int mPreviousDrawedPosition;

    public static SongsFragment getInstance() {
        if (mNewInstance == null) {
            mNewInstance = new SongsFragment();
        }
        return mNewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        checkNotNull(bundle);
        mSongs = bundle.getParcelableArrayList(Constant.BUNDLE_MUSICS);
        mCurrentSongPosition = bundle.getInt(Constant.ARGUMENT_MUSIC_POSITION);
        mPreviousDrawedPosition = -1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        bindView(view);
        initComponents();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDrawedItem();
    }

    @Override
    public void onSongItemCLick(View v, Song song, int position) {
        updateCurrentSongPosition(position);
        updateDrawedItem();
        mOnSongsFragmentListener.onNewSongItemClick(position);
    }

    @Override
    public void onSongItemMoreClick(View v, Song song, int position) {

    }

    public void bindView(View view) {
        mRecyclerViewSongs = view.findViewById(R.id.recycler_current_list);
    }

    public void initComponents() {
        mSongAdapter = new SongDetailsAdapter(getContext(),
                mSongs, this);
        mSongAdapter.updateDrawItem(mCurrentSongPosition);
        mRecyclerViewSongs.setAdapter(mSongAdapter);
        mLayoutManager =
                ((LinearLayoutManager) mRecyclerViewSongs.getLayoutManager());
    }

    /**
     * Update current and previous position of song playing
     * @param currentSongPosition
     */
    public void updateCurrentSongPosition(int currentSongPosition) {
        if (mPreviousDrawedPosition != mCurrentSongPosition) {
            mPreviousDrawedPosition = mCurrentSongPosition;
            mCurrentSongPosition = currentSongPosition;
        }
    }

    /**
     * Draw and remove highlight for item
     */
    public void updateDrawedItem() {
        mSongAdapter.updateDrawItem(mCurrentSongPosition);
    }

    public void setOnSongsFragmentListener(OnSongsFragmentListener onSongsFragmentListener) {
        mOnSongsFragmentListener = onSongsFragmentListener;
    }

    public interface OnSongsFragmentListener {
        void onNewSongItemClick(int position);
    }
}
