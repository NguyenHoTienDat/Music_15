package com.framgia.dattien.musicproject.screen.genredetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by tiendatbkhn on 25/05/2018.
 */

public abstract class BaseSongAdapter
        extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    protected final int VIEW_TYPE_ITEM = 0;
    protected final int VIEW_TYPE_LOADING = 1;

    protected Context mContext;
    protected List mSongs;
    protected OnSongDetailsItemClickListener mOnItemClickListener;

    protected abstract BaseSongViewHolder getViewHolder(Context context,
                                                        View itemView,
                                                        OnSongDetailsItemClickListener listener);

    public BaseSongAdapter(@NonNull Context context, List songs,
                           OnSongDetailsItemClickListener onItemClickListener) {
        super(context);
        mContext = context;
        mSongs = songs;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_load_more, parent, false);
                return new LoadingHolder(view);
            case VIEW_TYPE_ITEM:
            default:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_song_genre, parent, false);
                return getViewHolder(mContext, view, mOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_LOADING:
                break;
            case VIEW_TYPE_ITEM:
            default:
                ((BaseSongViewHolder) holder).setData((Song) mSongs.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mSongs.get(position) instanceof Song) {
            return VIEW_TYPE_ITEM;
        }
        return VIEW_TYPE_LOADING;
    }

    /**
     * Update recyclerView when has new data
     * We not update all of data set, just from the last position of previous list
     *
     * @param songs
     */
    public void updateData(List<Song> songs) {
        int lastPreviousPosition = mSongs.size() - 1;
        mSongs.addAll(songs);
        notifyItemRangeInserted(lastPreviousPosition, songs.size());
    }

    /**
     * Loading ViewHolder
     */
    static class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnSongDetailsItemClickListener {
        void onSongItemCLick(View v, Song song, int position);

        void onSongItemMoreClick(View v, Song song, int position);
    }
}
