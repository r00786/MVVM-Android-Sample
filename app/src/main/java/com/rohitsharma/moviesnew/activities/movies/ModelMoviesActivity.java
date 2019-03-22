package com.rohitsharma.moviesnew.activities.movies;

import android.util.Log;

import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.activities.movies.pojos.response.MoviesResponse;
import com.rohitsharma.moviesnew.basemodules.BaseModel;
import com.rohitsharma.moviesnew.data.DataManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class ModelMoviesActivity extends BaseModel {
    private static final String TAG = "ModelMoviesActivity";

    @Inject
    public ModelMoviesActivity(DataManager dataManager) {
        super(dataManager);
    }

    public void getMoviesModel(Consumer<MoviesResponse> success) {
        getDisposable().add(getDataManager().getMoviesrequest().
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(success, error -> Log.e(TAG, "getMoviesModel: ", error)));
    }

    public Predicate<Datum> filterFunctionImplementation(String text) {
        return filter ->
                ((filter.getGenre() + filter.getTitle()).toLowerCase().trim()).contains(text.toLowerCase().trim());
    }

    public Single<List<Datum>> getObservableForSearch(Predicate<Datum> fun, List<Datum> items) {
        return Observable.fromIterable(items).filter(fun).toList();
    }


}

