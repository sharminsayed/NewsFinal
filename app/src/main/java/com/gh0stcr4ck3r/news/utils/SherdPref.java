package com.gh0stcr4ck3r.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SherdPref {
    private Context context;

    public SherdPref(Context context) {
        this.context = context;
    }

    public  String getToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(BaseConstant.LOGIN_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString("token","");
    }

    public String getID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(BaseConstant.LOGIN_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }

    public void saveTokenAndID(String token, String id) {
        SharedPreferences.Editor editor=context.getSharedPreferences(BaseConstant.LOGIN_PREF,Context.MODE_PRIVATE).edit();
        editor.putString("token",token);
        editor.putString("id", id);
        editor.apply();

    }

    public boolean isLoggedIn(){
        return !getToken().isEmpty();
    }

    public void clearToken() {
        SharedPreferences.Editor editor = context.getSharedPreferences(BaseConstant.LOGIN_PREF, Context.MODE_PRIVATE).edit();
        editor.putString("token", "");
        editor.apply();
    }

}

