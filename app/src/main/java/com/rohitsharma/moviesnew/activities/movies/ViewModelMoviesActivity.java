package com.rohitsharma.moviesnew.activities.movies;

import android.annotation.SuppressLint;

import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.basemodules.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Predicate;

public class ViewModelMoviesActivity extends BaseViewModel<List<Datum>, ModelMoviesActivity> {

    @Inject
    public ViewModelMoviesActivity(ModelMoviesActivity model) {
        super(model);
    }

    public void getMovies() {
        getModel().getMoviesModel(success -> {
            if (success == null) {
                getModel().someErrorIsThere();
                return;
            }
            getModel().cacheData(success.getData());
            getMutableLiveData().setValue(success.getData());
        });
    }

    public void checkIfDataAvInCache(boolean isCacheCleared) {
        boolean isDataAvailable = getModel().checkDataAvailableInCache(Datum.class, isCacheCleared);
        if (!isDataAvailable) {
            getMovies();
        } else {
            getMutableLiveData().setValue(getCachedData());
        }
    }


    public List<Datum> getCachedData() {
        List<Datum> cachedData = getModel().getCachedData(Datum.class);
        if (cachedData == null) {
            getModel().someErrorIsThere();
            return new ArrayList<>();
        }
        return cachedData;
    }

    public Predicate<Datum> filterResults(String text) {
        return getModel().filterFunctionImplementation(text);
    }


    @SuppressLint("CheckResult")
    public Single<List<Datum>> getObsForSearch(Predicate<Datum> fun, List<Datum> items) {
        return getModel().getObservableForSearch(fun, items);
    }


}
