package com.nandaiqbalh.tugaspbb.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private final String USER_PREF = "USER_PREF";
    SharedPreferences sharedPref;

    // status login
    String login = "login";

    private int id = 0;
    private String name = "name";
    private String email = "email";
    private String phone = "phone";
    private String address = "address";


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


    public void setString(String key, String value) {
        sharedPref.edit().putString(key, value).apply();
    }

    public String getString(String key){
        return sharedPref.getString(key, "");
    }

    public void setInt(int key, int value) {
        sharedPref.edit().putInt(String.valueOf(key), value).apply();
    }

    public int getInt(int key){
        return sharedPref.getInt(String.valueOf(key), 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
