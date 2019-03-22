package com.rohitsharma.moviesnew.activities.movies;

import android.widget.ImageView;
import android.widget.TextView;

import com.rohitsharma.moviesnew.R;

import javax.inject.Inject;

import butterknife.BindView;

public class ViewInjectionRvMovies {
    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.tv_genre)
    TextView tvGenre;
    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;
    @BindView(R.id.tv_year)
    TextView tvYear;

    @Inject
    public ViewInjectionRvMovies() {
    }
}
