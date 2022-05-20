package com.nandaiqbalh.tugaspbb.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private final String USER_PREF = "USER_PREF";
    SharedPreferences sharedPref;

    // status login
    String login = "login";

    public SharedPrefs(Activity activity) {
        sharedPref = activity.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
    }

    public void setValues(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValues(String key) {
        return sharedPref.getString(key, "");
    }

    public void setStatusLogin(boolean statusLogin) {
        sharedPref.edit().putBoolean(login, statusLogin).apply();

    }

    public boolean getStatusLogin() {
        return sharedPref.getBoolean(login, false);
    }

}
