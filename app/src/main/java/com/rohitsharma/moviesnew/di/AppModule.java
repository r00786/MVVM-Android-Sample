package com.rohitsharma.moviesnew.di;

import android.content.Context;

import com.rohitsharma.moviesnew.MoviesApplication;
import com.rohitsharma.moviesnew.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context providesApplicationContext(MoviesApplication daggerApplication) {
        return daggerApplication;
    }

    @Provides
    DataManager providesDataManager() {
        return new DataManager();
    }
}
