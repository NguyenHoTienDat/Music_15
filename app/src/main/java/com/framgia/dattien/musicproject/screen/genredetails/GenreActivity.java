package com.framgia.dattien.musicproject.screen.genredetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
        SongDetailsAdapter.OnSongDetailsItemClickListener, View.OnClickListener {

    private static final String ARGUMENT_GENRE = "ARGUMENT_GENRE";

    private ImageView mImageGenre;
    private ImageView mImageBack;
    private TextView mTextActionPlayGenre;
    private RecyclerView mRecyclerViewGenreSongs;

    private Genre mGenre;
    private GenreContract.Presenter mPresenter;
    private SongDetailsAdapter mSongAdapter;
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

        initComponents();
        initPresenter(musicRepository);
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
        mSongs = songs;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSongItemCLick(View v, Song song, int position) {
        playSong(mSongs, position);
        startActivity(MusicPlayerActivity.getMusicPlayerScreenIntent(this));
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

    public void getGenre() {
        checkNotNull(getIntent().getParcelableExtra(ARGUMENT_GENRE));
        mGenre = getIntent().getParcelableExtra(ARGUMENT_GENRE);
    }

    public void initComponents() {
        mSongAdapter = new SongDetailsAdapter(this,
                new ArrayList<Song>(), this);
        mRecyclerViewGenreSongs.setAdapter(mSongAdapter);

        checkNotNull(mGenre);
        Glide.with(this).load(mGenre.getGenreAvatar())
                .placeholder(R.drawable.ic_head_phone)
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
}
