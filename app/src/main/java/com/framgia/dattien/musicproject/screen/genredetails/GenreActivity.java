package com.framgia.dattien.musicproject.screen.genredetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.dattien.musicproject.R;
import com.framgia.dattien.musicproject.data.model.Genre;
import com.framgia.dattien.musicproject.data.model.Song;
import com.framgia.dattien.musicproject.data.repository.MusicRepository;
import com.framgia.dattien.musicproject.data.source.local.MusicLocalDataSource;
import com.framgia.dattien.musicproject.data.source.remote.MusicRemoteDataSource;
import com.framgia.dattien.musicproject.screen.BasicActivity;
import com.framgia.dattien.musicproject.screen.musicplay.MusicPlayerActivity;
import com.framgia.dattien.musicproject.service.MusicPlayerService;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GenreActivity extends BasicActivity implements GenreContract.View,
        BaseSongAdapter.OnSongDetailsItemClickListener , View.OnClickListener {

    private static final String ARGUMENT_GENRE = "ARGUMENT_GENRE";
    private static final int DEFAULT_POSITION = 0;

    private ImageView mImageGenre;
    private ImageView mImageBack;
    private TextView mTextActionPlayGenre;
    private RecyclerView mRecyclerViewGenreSongs;

    private Genre mGenre;
    private GenreContract.Presenter mPresenter;
    private SongLoadMoreAdapter mSongAdapter;
    private List<Song> mSongs;

    public static Intent getGenreIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, GenreActivity.class);
        intent.putExtra(GenreActivity.ARGUMENT_GENRE, genre);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        bindView();
        registerListener();
        getGenre();

        MusicRepository musicRepository =
                MusicRepository.getInstance(MusicLocalDataSource.getInstance(this),
                        MusicRemoteDataSource.getInstance(this));
        initPresenter(musicRepository);
        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void bindView() {
        mRecyclerViewGenreSongs = findViewById(R.id.rv_genre_songs);
        mImageGenre = findViewById(R.id.image_genre);
        mImageBack = findViewById(R.id.image_genre_back);
        mTextActionPlayGenre = findViewById(R.id.text_play_genre);
    }

    @Override
    public void initPresenter(MusicRepository musicRepository) {
        mPresenter = new GenrePresenter(musicRepository);
        mPresenter.setView(this);
        mPresenter.setGenre(mGenre);
    }

    @Override
    public void updateSongsByGenre(List<Song> songs) {
        mSongAdapter.updateData(songs);
        showLoadMoreProgress();
        mSongs = songs;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadMoreProgress() {
        hideLoadMoreProgress();
        mSongAdapter.addLoadMoreItem();
    }

    @Override
    public void hideLoadMoreProgress() {
        mSongAdapter.removeLoadMoreItem();
    }

    @Override
    public void onSongItemCLick(View v, Song song, int position) {

    }

    @Override
    public void onSongItemMoreClick(View v, Song song, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_genre_back:
                finish();
                break;

            case R.id.text_play_genre:

                break;

            default:
                break;
        }
    }

    public void beginMusicTask(int position) {
        if (mSongs != null && mSongs.size() > 0) {
            playSong(mSongs, position);
            startActivity(MusicPlayerActivity.getMusicPlayerScreenIntent(this));
        }
    }

    public void getGenre() {
        checkNotNull(getIntent().getParcelableExtra(ARGUMENT_GENRE));
        mGenre = getIntent().getParcelableExtra(ARGUMENT_GENRE);
    }

    public void initComponents() {
        mSongs = new ArrayList<>();
        mSongAdapter = new SongLoadMoreAdapter(this,
                mSongs, this);
        addRecylerViewScrollListener();
        mRecyclerViewGenreSongs.setAdapter(mSongAdapter);
        checkNotNull(mGenre);
        Glide.with(this).load(mGenre.getGenreAvatar())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_head_phone))
                .into(mImageGenre);
    }

    public void registerListener() {
        mImageBack.setOnClickListener(this);
        mTextActionPlayGenre.setOnClickListener(this);
    }

    private void playSong(List<Song> songs, int currentPosition) {
        startService(MusicPlayerService.
                getPlaySongIntent(this, songs, currentPosition));
    }

    private void addRecylerViewScrollListener() {
        final LinearLayoutManager linearLayoutManager =
                (LinearLayoutManager) mRecyclerViewGenreSongs.getLayoutManager();
        mRecyclerViewGenreSongs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount > 1 && totalItemCount <= (lastVisibleItem + 1)) {
                    mPresenter.loadMoreData();
                }
            }
        });
    }
}
