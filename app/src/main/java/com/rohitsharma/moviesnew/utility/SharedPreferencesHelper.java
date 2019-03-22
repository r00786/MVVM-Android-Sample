package com.rohitsharma.moviesnew.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

/**
 * Shared Preferences Singleton
 *
 * @author Rohit at 8/3/2017
 */

public class SharedPreferencesHelper {
    private static SharedPreferencesHelper msharedPreferencesHelper;
    private SharedPreferences msharedPreferences;

    private SharedPreferencesHelper(Context vContext, String key) {
        if (vContext != null) {
            msharedPreferences = vContext.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
    }

    public static synchronized SharedPreferencesHelper getInstance(Context vContext, String key) {

        if (msharedPreferencesHelper == null) {
            msharedPreferencesHelper = new SharedPreferencesHelper(vContext, key);
        }
        return msharedPreferencesHelper;
    }

    public void putString(String key, String value) {
        msharedPreferences.edit().putString(key, value).apply();

    }

    public void putStringSet(String key, Set<String> value) {
        msharedPreferences.edit().putStringSet(key, value).apply();
    }

    public void putBoolean(String key, Boolean value) {
        msharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void putInt(String key, int value) {
        msharedPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return msharedPreferences.getInt(key, defValue);
    }

    public String getString(String key) {
        return msharedPreferences.getString(key, "");
    }




    public Boolean getBoolean(String key) {
        return msharedPreferences.getBoolean(key, false);
    }


    public void clearData() {
        SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public <T> void putObject(T object, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        msharedPreferences.edit().putString(key, json).apply();
    }

    public <T> T getObject(String key, Class<T> cl) {
        Gson gson = new Gson();
        String json = msharedPreferences.getString(key, "");
        return gson.fromJson(json, cl);
    }


}
