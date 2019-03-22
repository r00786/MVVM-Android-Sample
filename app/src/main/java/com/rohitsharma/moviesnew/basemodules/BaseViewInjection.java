package com.rohitsharma.moviesnew.basemodules;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseViewInjection<T extends BaseActivity> {
    public Unbinder bind;


    public void injectView(T activity) {
        activity.setContentView(activity.getContainerId());
        bind = ButterKnife.bind(this, activity);
        initClicks(activity);

    }


    public void initClicks(final T activity) {

    }


    public void unBind() {
        bind.unbind();
    }
}
