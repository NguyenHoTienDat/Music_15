package com.framgia.dattien.musicproject.screen.main.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.screen.BaseRecyclerViewAdapter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

public class SongAdapter extends BaseRecyclerViewAdapter<SongAdapter.TopSongHolder> {

    private Context mContext;
    private List<Song> mSongs;
    private OnSongItemClickListener mOnItemClickListener;

    public SongAdapter(@NonNull Context context, List<Song> songs,
                       OnSongItemClickListener onItemClickListener) {
        super(context);
        mContext = context;
        mSongs = songs;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public TopSongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_song, parent, false);
        return new TopSongHolder(view, mContext, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(TopSongHolder holder, int position) {
        holder.setData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    public void updateData(List<Song> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    static class TopSongHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Context mContext;
        private Song mSong;
        private OnSongItemClickListener mOnItemClickListener;

        private TextView mTextSongTitle;
        private TextView mTextSinger;
        private TextView mTextListenCount;
        private ImageView mImageSong;

        public TopSongHolder(View itemView, Context context,
                             OnSongItemClickListener onItemClickListener) {
            super(itemView);
            mContext = context;
            mTextSongTitle = itemView.findViewById(R.id.text_item_song_title);
            mTextSinger = itemView.findViewById(R.id.text_item_song_singer);
            mTextListenCount = itemView.findViewById(R.id.text_item_listen_count);
            mImageSong = itemView.findViewById(R.id.image_item_song);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void setData(Song song) {
            checkNotNull(song);
            mSong = song;

            mTextSongTitle.setText(mSong.getTitle());
            mTextSinger.setText(mSong.getUser().getUserName());
            mTextListenCount.setText(mSong.getPlaybackCount() + "");
            Glide.with(mContext).load(mSong.getArtworkUrl())
                    .placeholder(R.drawable.ic_head_phone)
                    .into(mImageSong);
        }

        @Override
        public void onClick(View v) {
            checkNotNull(mOnItemClickListener).onItemSongClick(v, mSong, getAdapterPosition());
        }
    }

    public interface OnSongItemClickListener {
        void onItemSongClick(View v, Song song, int position);
    }
}
