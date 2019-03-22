package com.rohitsharma.moviesnew.activities.movies;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rohitsharma.moviesnew.R;
import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.basemodules.BaseActivity;
import com.rohitsharma.moviesnew.basemodules.GenericRecyclerAdapter;
import com.rohitsharma.moviesnew.service.ClearCacheService;
import com.rohitsharma.moviesnew.utility.Constants;
import com.rohitsharma.moviesnew.utility.ItemDecor;
import com.rohitsharma.moviesnew.utility.TextWatcherHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoviesActivity extends BaseActivity<ViewInjectionMovies, MoviesActivity> {
    @Inject
    ViewInjectionMovies injection;
    @Inject
    ViewInjectionRvMovies injectionRv;
    @Inject
    GenericRecyclerAdapter<Datum> adapter;
    @Inject
    ItemDecor itemDecor;
    @Inject
    ViewModelProvider.Factory vMFactory;
    ViewModelMoviesActivity vMMoviesActivity;

    @Override
    public int getContainerId() {
        return R.layout.activity_main;
    }

    @Override
    public ViewInjectionMovies getViewInjection() {
        return injection;
    }

    @Override
    public MoviesActivity getActivityInstance() {
        return this;
    }

    @Override
    public void init() {
        initializeGlobalVariables();
        setupRecycler();
        initObservers();
        checkIfDataAvailableInCache();
    }

    /**
     * initializing the View Model from the view model factory to maintain a single instance
     */
    private void initializeGlobalVariables() {
        vMMoviesActivity = ViewModelProviders.of(this, vMFactory).get(ViewModelMoviesActivity.class);
    }

    /**
     * Checking whether if the data is available in cache and whether it is time to sync data
     * or not
     */
    private void checkIfDataAvailableInCache() {
        showLoading();
        vMMoviesActivity.checkIfDataAvInCache(getSharedPreferencesHelper().getBoolean(Constants.ClEAR_CACHE));
    }

    /**
     * Observing the Live Data
     */
    private void initObservers() {

        vMMoviesActivity.getMutableLiveData().observe(this, successResponse -> {
                    adapter.addData(successResponse);
                    setSearchListener(successResponse);
                    hideLoading();
                    if (!getSharedPreferencesHelper().getBoolean(Constants.ClEAR_CACHE)) {
                        setAlarmToClearData();
                    }
                }
        );

    }

    /**
     * setting up the Recycler View
     */
    private void setupRecycler() {
        injection.rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        injection.rvMovies.addItemDecoration(itemDecor);
        injection.rvMovies.setAdapter(adapter);
        adapter.setBindListener((obj, viewHolder) -> {
            ButterKnife.bind(injectionRv, viewHolder.itemView);
            if (obj == null) return;
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(new ColorDrawable(Color.BLACK));
            requestOptions.error(new ColorDrawable(Color.BLACK));
            Glide.with(viewHolder.itemView.getContext()).load(checkNotNull(obj.getPoster())).apply(requestOptions).into(injectionRv.ivMovie);
            injectionRv.tvGenre.setText(checkNotNull(obj.getGenre()));
            injectionRv.tvYear.setText(checkNotNull(obj.getYear()));
            injectionRv.tvMovieName.setText(checkNotNull(obj.getTitle()));
        });


    }

    @SuppressLint("CheckResult")
    private void setSearchListener(List<Datum> items) {
        TextWatcherHelper.getObservable(injection.etSearch).debounce(800, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).map(s -> s).subscribe(
                text -> vMMoviesActivity.getObsForSearch(vMMoviesActivity.filterResults(text), items).subscribe(success -> adapter.addData(success), logError(this.getClass().getName())),
                logError(this.getClass().getName())
        );
    }


    /**
     * Caching data for 10 minutes so if user comes back after 10 minutes the data will get synced
     * from server
     */
    public void setAlarmToClearData() {
        Intent intent = new Intent(this, ClearCacheService.class);
        PendingIntent pintent = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? PendingIntent.getForegroundService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                : PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm != null) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 600 * 1000, pintent);
        }
        getSharedPreferencesHelper().putBoolean(Constants.ClEAR_CACHE, true);

    }


}
