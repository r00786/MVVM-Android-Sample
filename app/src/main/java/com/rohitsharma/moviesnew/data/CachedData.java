package com.rohitsharma.moviesnew.data;

import java.util.List;

import io.realm.RealmObject;

public interface CachedData {
    public <T extends RealmObject> void setRealmData(List<T> itemsToSaveLocally);

    public <T extends RealmObject> List<T> getRealmResults(Class<T> objType);
}
