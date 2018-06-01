package com.framgia.dattien.musicproject.screen.genredetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

/**
 * Created by tiendatbkhn on 31/05/2018.
 */

public class SongLoadMoreAdapter extends BaseSongAdapter {
    protected final String LOAD_MORE_ITEM = "LOAD_MORE_ITEM";

    public SongLoadMoreAdapter(@NonNull Context context,
                               List songs,
                               OnSongDetailsItemClickListener onItemClickListener) {
        super(context, songs, onItemClickListener);
    }

    @Override
    public BaseSongViewHolder getViewHolder(Context context,
                                            View itemView,
                                            OnSongDetailsItemClickListener listener) {
        return new BaseSongViewHolder(context, itemView, listener);
    }

    /**
     * Add item with special type to display progress bar
     */
    public void addLoadMoreItem() {
        mSongs.add(LOAD_MORE_ITEM);
        notifyItemInserted(mSongs.size() - 1);
    }

    /**
     * Remove item of progress bar when load more done
     */
    public void removeLoadMoreItem() {
        int index = mSongs.indexOf(LOAD_MORE_ITEM);
        if (index != -1) {
            mSongs.remove(LOAD_MORE_ITEM);
            notifyItemRemoved(index);
        }
    }
}
