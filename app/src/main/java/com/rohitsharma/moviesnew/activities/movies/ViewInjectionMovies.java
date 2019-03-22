package com.rohitsharma.moviesnew.activities.movies;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.rohitsharma.moviesnew.R;
import com.rohitsharma.moviesnew.basemodules.BaseViewInjection;

import javax.inject.Inject;

import butterknife.BindView;

public class ViewInjectionMovies extends BaseViewInjection<MoviesActivity> {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @Inject
    public ViewInjectionMovies() {
    }

    @Override
    public void initClicks(MoviesActivity activity) {
        super.initClicks(activity);
    }

}
