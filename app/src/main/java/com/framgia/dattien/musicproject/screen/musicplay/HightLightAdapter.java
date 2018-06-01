package com.framgia.dattien.musicproject.screen.musicplay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.genredetails.BaseSongAdapter;
import com.framgia.dattien.musicproject.screen.genredetails.BaseSongViewHolder;

import java.util.List;

/**
 * Created by tiendatbkhn on 31/05/2018.
 */

public class HightLightAdapter extends BaseSongAdapter {
    private int mCurrentSongPosition;
    private int mPreviousSongPosition;

    @Override
    public BaseSongViewHolder getViewHolder(Context context, View itemView,
                                            OnSongDetailsItemClickListener listener) {
        return new ViewHolder(context, itemView, listener);
    }

    public HightLightAdapter(@NonNull Context context,
                             List songs,
                             OnSongDetailsItemClickListener onItemClickListener) {
        super(context, songs, onItemClickListener);
        mCurrentSongPosition = -1;
        mPreviousSongPosition = -1;
    }

    /**
     * Update highlight for current song
     *
     * @param position
     */
    public void updateCurrentHightLight(int position) {
        if (position == -1) {
            return;
        }
        int temp = mPreviousSongPosition;
        mPreviousSongPosition = mCurrentSongPosition;
        mCurrentSongPosition = position;
        if (temp != -1) {
            notifyItemChanged(temp);
            notifyItemChanged(mPreviousSongPosition);
        }
        notifyItemChanged(mCurrentSongPosition);
    }

    public class ViewHolder extends BaseSongViewHolder {

        public ViewHolder(Context context,
                          View itemView,
                          OnSongDetailsItemClickListener onItemClickListener) {
            super(context, itemView, onItemClickListener);
        }

        @Override
        public void setData(Song song) {
            super.setData(song);
            if (mCurrentSongPosition == getAdapterPosition()) {
                mTextSongTitle
                        .setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                mTextSongTitle.setSelected(true);
                mImagePlayingGif.setVisibility(View.VISIBLE);
            } else {
                mTextSongTitle
                        .setTextColor(mContext.getResources().getColor(android.R.color.black));
                mTextSongTitle.setSelected(false);
                mImagePlayingGif.setVisibility(View.GONE);
            }
        }
    }
}
