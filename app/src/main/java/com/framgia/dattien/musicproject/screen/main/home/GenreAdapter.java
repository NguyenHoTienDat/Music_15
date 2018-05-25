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
import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.screen.BaseRecyclerViewAdapter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tiendatbkhn on 24/05/2018.
 */

public class GenreAdapter extends BaseRecyclerViewAdapter<GenreAdapter.GenreHolder> {

    private Context mContext;
    private List<Genre> mGenres;
    private OnGenreItemClickListener mOnItemClickListener;

    public GenreAdapter(@NonNull Context context, List<Genre> genres,
                        OnGenreItemClickListener onItemClickListener) {
        super(context);
        mContext = context;
        mGenres = genres;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public GenreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_genre, parent, false);
        return new GenreHolder(view, mContext, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(GenreHolder holder, int position) {
        holder.setData(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    public void updateData(List<Genre> genres) {
        mGenres.clear();
        mGenres.addAll(genres);
        notifyDataSetChanged();
    }

    static class GenreHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Context mContext;
        private Genre mGenre;
        private OnGenreItemClickListener mOnItemClickListener;

        private TextView mTextGenreName;
        private ImageView mImageGenre;

        public GenreHolder(View itemView, Context context,
                           OnGenreItemClickListener onItemClickListener) {
            super(itemView);
            mContext = context;
            mTextGenreName = itemView.findViewById(R.id.text_item_genre_name);
            mImageGenre = itemView.findViewById(R.id.image_item_genre);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void setData(Genre genre) {
            checkNotNull(genre);
            mGenre = genre;
            mTextGenreName.setText(mGenre.getGenreName());
            Glide.with(mContext).load(mGenre.getGenreAvatar())
                    .placeholder(R.drawable.ic_head_phone)
                    .into(mImageGenre);
        }

        @Override
        public void onClick(View v) {
            checkNotNull(mOnItemClickListener).onItemGenreClick(v, mGenre, getAdapterPosition());
        }
    }

    public interface OnGenreItemClickListener {
        void onItemGenreClick(View v, Genre genre, int position);
    }
}
