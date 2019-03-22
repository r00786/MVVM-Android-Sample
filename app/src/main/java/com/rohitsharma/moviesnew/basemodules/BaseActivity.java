package com.rohitsharma.moviesnew.basemodules;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rohitsharma.moviesnew.R;
import com.rohitsharma.moviesnew.utility.Constants;
import com.rohitsharma.moviesnew.utility.SharedPreferencesHelper;



import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.functions.Consumer;

public abstract class BaseActivity<T extends BaseViewInjection, V extends BaseActivity> extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private ProgressDialog mProgressDialog;

    /**
     * A common method for showing progress
     *
     * @param context context of the activity for which the progress is to be shown
     * @return instance of progress dialog
     */
    public ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        if (!isFinishing()) {
            progressDialog.show();
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

        }
        return progressDialog;
    }

    public ProgressDialog showLoadingDialogWithText(Context context, String text) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        if (!isFinishing()) {
            progressDialog.show();
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            TextView tv = progressDialog.findViewById(R.id.tv_text);
            tv.setText(text);

        }
        return progressDialog;
    }


    public boolean isTimeAutomatic(Context c) {
        return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        initiateActivity();
    }


    void initiateActivity() {
        if (getViewInjection() != null) {
            getViewInjection().injectView(getActivityInstance());
        } else {
            setContentView(getContainerId());
            ButterKnife.bind(getActivityInstance());
        }

        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this, Constants.PREF_KEY);
        init();
    }

    /**
     * This is a common method for hiding the progress dialog
     */
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if (!isFinishing()) {
                mProgressDialog.cancel();
            }
        }
    }

    /**
     * This is a common method for showing the progress dialog if there is any
     */
    public void showLoading() {
        hideLoading();
        if (!isFinishing()) {
            mProgressDialog = showLoadingDialog(this);
        }
    }

    public void showLoading(String text) {
        hideLoading();
        if (!isFinishing()) {
            mProgressDialog = showLoadingDialogWithText(this, text);
        }
    }


    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastWithGravity(String text, int gravity) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }


    public abstract int getContainerId();

    public void openNextActivity(Activity context, Class<?> activityToOpen, Bundle bundle, boolean finishCurrent) {
        Intent intent = new Intent(context, activityToOpen);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (finishCurrent) {
            context.finish();
        }
        context.startActivity(intent);
    }

    @NonNull
    public String checkNotNull(String str) {
        return str != null ? str : "";
    }


    public abstract T getViewInjection();

    public abstract V getActivityInstance();

    public abstract void init();


    public SharedPreferencesHelper getSharedPreferencesHelper() {
        return sharedPreferencesHelper;
    }

    public Consumer<Throwable> logError(String clazzName) {
        return error -> Log.e(clazzName, error.getMessage());
    }
}
