package com.rohitsharma.moviesnew.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.utility.Constants;
import com.rohitsharma.moviesnew.utility.SharedPreferencesHelper;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.realm.Realm;

public class ClearCacheManager extends Worker {
    public ClearCacheManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.delete(Datum.class));
        SharedPreferencesHelper sp = SharedPreferencesHelper.getInstance(getApplicationContext(), Constants.PREF_KEY);
        sp.putBoolean(Constants.ClEAR_CACHE, false);
        return Result.success();
    }
}
