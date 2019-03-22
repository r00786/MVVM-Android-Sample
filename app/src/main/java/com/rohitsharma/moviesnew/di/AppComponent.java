package com.rohitsharma.moviesnew.di;

import com.rohitsharma.moviesnew.MoviesApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MoviesApplication networkApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MoviesApplication application);

        AppComponent build();
    }

}
