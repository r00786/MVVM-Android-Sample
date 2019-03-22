package com.rohitsharma.moviesnew.basemodules;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public abstract class BaseViewModel<T, V extends BaseModel> extends ViewModel {

    private final V model;
    private MutableLiveData<T> mutableLiveData;


    public BaseViewModel(V model) {
        this.model = model;
        initArchComponents();
    }

    private void initArchComponents() {
        mutableLiveData = new MutableLiveData<>();

    }

    @Override
    protected void onCleared() {
        model.dispose();
        super.onCleared();
    }

    public MutableLiveData<T> getMutableLiveData() {
        return mutableLiveData;
    }


    public V getModel() {
        return model;
    }


}
