package com.rohitsharma.moviesnew.basemodules;

import butterknife.Unbinder;

public class BaseViewInjectionFragment {
    public Unbinder bind;

    public void unBind() {

        if (bind != null)
            bind.unbind();
    }

}
