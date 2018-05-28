package com.framgia.dattien.musicproject.screen.genredetails;

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
 * Created by tiendatbkhn on 25/05/2018.
 */

public class SongDetailsAdapter
        extends BaseRecyclerViewAdapter<SongDetailsAdapter.SongDetailsHolder> {

    private Context mContext;
    private List<Song> mSongs;
    private OnSongDetailsItemClickListener mOnItemClickListener;

    public SongDetailsAdapter(@NonNull Context context, List<Song> songs,
                              OnSongDetailsItemClickListener onItemClickListener) {
        super(context);
        mContext = context;
        mSongs = songs;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public SongDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_song_genre, parent, false);
        return new SongDetailsHolder(view, mContext, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(SongDetailsHolder holder, int position) {
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

    static class SongDetailsHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Context mContext;
        private Song mSong;
        private OnSongDetailsItemClickListener mOnItemClickListener;

        private TextView mTextSongTitle;
        private TextView mTextSinger;
        private TextView mTextListenCount;
        private ImageView mImageSong;
        private ImageView mImagePlayingGif;

        public SongDetailsHolder(View itemView, Context context,
                                 OnSongDetailsItemClickListener onItemClickListener) {
            super(itemView);
            mContext = context;
            mTextSongTitle = itemView.findViewById(R.id.text_item_song_title);
            mTextSinger = itemView.findViewById(R.id.text_item_song_singer);
            mTextListenCount = itemView.findViewById(R.id.text_item_listen_count);
            mImageSong = itemView.findViewById(R.id.image_item_song);
            mImagePlayingGif = itemView.findViewById(R.id.image_item_play_gif);
            mOnItemClickListener = onItemClickListener;
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

        public void setData(Song song) {
            checkNotNull(song);
            mSong = song;

            mTextSongTitle.setText(mSong.getTitle());
            mTextSinger.setText(mSong.getUser().getUserName());
            mTextListenCount.setText(mSong.getPlaybackCount() + "");
            Glide.with(mContext).load(mSong.getArtworkUrl())
                    .placeholder(R.drawable.ic_head_phone)
                    .into(mImageSong);

            Glide.with(mContext)
                    .load(R.drawable.gif_playing)
                    .asGif()
                    .placeholder(R.drawable.ic_head_phone)
                    .crossFade()
                    .into(mImagePlayingGif);
        }

    }

    public interface OnSongDetailsItemClickListener {
        void onSongItemCLick(View v, Song song, int position);

        void onSongItemMoreClick(View v, Song song, int position);
    }
}
