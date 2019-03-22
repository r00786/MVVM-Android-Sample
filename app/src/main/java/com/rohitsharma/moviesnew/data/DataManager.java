package com.rohitsharma.moviesnew.data;

import com.rohitsharma.moviesnew.activities.movies.pojos.response.MoviesResponse;
import com.rohitsharma.moviesnew.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmObject;

public class DataManager implements Data {
    @Override
    public <T extends RealmObject> void setRealmData(List<T> itemsToSaveLocally) {
        Realm realm = Realm.getDefaultInstance();
        for (T t : itemsToSaveLocally) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(t);
            realm.commitTransaction();
        }


    }

    @Override
    public <T extends RealmObject> List<T> getRealmResults(Class<T> objType) {
        Realm realm = Realm.getDefaultInstance();
        return new ArrayList<>(realm.copyFromRealm(realm.where(objType).findAll()));

    }

    @Override
    public Single<MoviesResponse> getMoviesrequest() {
        return RetrofitService.getClient().createService().getMoviesrequest();
    }
}
