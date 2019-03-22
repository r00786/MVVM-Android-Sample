package com.rohitsharma.moviesnew.basemodules;

import com.rohitsharma.moviesnew.data.DataManager;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmObject;

public abstract class BaseModel {


    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable;

    public BaseModel(DataManager dataManager) {
        this.dataManager = dataManager;
        initDisposable();
    }

    private void initDisposable() {
        compositeDisposable = new CompositeDisposable();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public <T extends RealmObject> void cacheData(List<T> data) {
        dataManager.setRealmData(data);
    }

    public <T extends RealmObject> List<T> getCachedData(Class<T> clazz) {
        return dataManager.getRealmResults(clazz);
    }

    public <T extends RealmObject> boolean checkDataAvailableInCache(Class<T> clazz, boolean isCacheCleared) {
        List<T> cachedData = getCachedData(clazz);
        return cachedData != null && cachedData.size() > 0 && isCacheCleared;
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public CompositeDisposable getDisposable() {
        return compositeDisposable;
    }

    public void someErrorIsThere() {
        //do something if response is null
    }
}
