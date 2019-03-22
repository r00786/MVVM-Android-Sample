package com.rohitsharma.moviesnew.di;

import com.rohitsharma.moviesnew.activities.movies.MoviesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = ModuleMoviesActivity.class)
    abstract MoviesActivity bindMoviesActivity();


}
