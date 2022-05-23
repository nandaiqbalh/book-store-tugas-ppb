package com.nandaiqbalh.tugaspbb.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nandaiqbalh.tugaspbb.model.User;

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

    private String user = "user";
    private String userUpdated = "userUpdated";


    Gson gson = new Gson();


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


    // Setter bertipe User untuk memanggil langsung semua field di dalam user, agar ga manggil satu per satu
    public void setUser(User value) {
        String data = gson.toJson(value, User.class); // ubah data dari bentuk Object Class ke dalam bentuk String
        sharedPref.edit().putString(String.valueOf(user), data).apply();
    }

    // Getter bertipe User untuk memanggil langsung semua field di dalam user, agar ga manggil satu per satu
    public User getUser(){
        String data;
        data = sharedPref.getString(String.valueOf(this.user), null); // ubah data dari bentuk String ke dalam bentuk Object Class

        if (data != null){
            return gson.fromJson(data, User.class);
        } else {
            return null;
        }
    }

    // Setter bertipe User untuk memanggil langsung semua field di dalam user, agar ga manggil satu per satu
    public void setUserUpdated(User value) {
        String data = gson.toJson(value, User.class); // ubah data dari bentuk Object Class ke dalam bentuk String
        sharedPref.edit().putString(String.valueOf(userUpdated), data).apply();
    }

    // Getter bertipe User untuk memanggil langsung semua field di dalam user, agar ga manggil satu per satu
    public User getUserUpdated(){
        String data;
        data = sharedPref.getString(String.valueOf(this.userUpdated), null); // ubah data dari bentuk String ke dalam bentuk Object Class

        if (data != null){
            return gson.fromJson(data, User.class);
        } else {
            return null;
        }
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
