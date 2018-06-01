package com.framgia.dattien.musicproject.screen.genredetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 31/05/2018.
 */

public class BaseSongViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    protected Context mContext;
    protected Song mSong;
    protected BaseSongAdapter.OnSongDetailsItemClickListener mOnItemClickListener;

    protected TextView mTextSongTitle;
    protected TextView mTextSinger;
    protected TextView mTextListenCount;
    protected ImageView mImageSong;
    protected ImageView mImagePlayingGif;

    public BaseSongViewHolder(Context context,
                              View itemView,
                              BaseSongAdapter.OnSongDetailsItemClickListener listener) {
        super(itemView);
        mContext = context;
        mTextSongTitle = itemView.findViewById(R.id.text_item_song_title);
        mTextSinger = itemView.findViewById(R.id.text_item_song_singer);
        mTextListenCount = itemView.findViewById(R.id.text_item_listen_count);
        mImageSong = itemView.findViewById(R.id.image_item_song);
        mImagePlayingGif = itemView.findViewById(R.id.image_item_play_gif);
        mOnItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_item_more:
                checkNotNull(mOnItemClickListener).
                        onSongItemMoreClick(v, mSong, getAdapterPosition());
                break;
            default:
                checkNotNull(mOnItemClickListener).
                        onSongItemCLick(v, mSong, getAdapterPosition());
                break;
        }
    }

    /**
     * Set data to UI
     * @param song
     */
    public void setData(Song song) {
        checkNotNull(song);
        mSong = song;
        mTextSongTitle.setText(mSong.getTitle());
        mTextSinger.setText(mSong.getUser().getUserName());
        mTextListenCount.setText(mSong.getPlaybackCount() + "");
        Glide.with(mContext).load(mSong.getArtworkUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_head_phone))
                .into(mImageSong);
    }
}
