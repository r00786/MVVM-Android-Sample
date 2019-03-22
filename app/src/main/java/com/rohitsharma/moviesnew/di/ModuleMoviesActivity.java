package com.rohitsharma.moviesnew.di;

import android.arch.lifecycle.ViewModelProvider;

import com.rohitsharma.moviesnew.R;
import com.rohitsharma.moviesnew.activities.movies.ModelMoviesActivity;
import com.rohitsharma.moviesnew.activities.movies.ViewModelMoviesActivity;
import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.basemodules.GenericRecyclerAdapter;
import com.rohitsharma.moviesnew.utility.ItemDecor;
import com.rohitsharma.moviesnew.utility.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleMoviesActivity {


    @Provides
    GenericRecyclerAdapter<Datum> providesAdapter() {
        return new GenericRecyclerAdapter<>(R.layout.layout_rv_movies);
    }

    @Provides
    ItemDecor providesItemDecor() {
        return new ItemDecor(16, 3);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(ViewModelMoviesActivity vm) {
        return new ViewModelProviderFactory<>(vm);
    }

    @Provides
    ViewModelMoviesActivity proviesViewModelMovies(ModelMoviesActivity modelMoviesActivity) {
        return new ViewModelMoviesActivity(modelMoviesActivity);
    }
}
