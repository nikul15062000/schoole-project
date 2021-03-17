package com.example.schoolapp.Global;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {

        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public boolean getusename() {
        String usename = prefs.getString("usename","");
        if(usename.isEmpty ()){
            return false;
        }
        return true;
    }

    public void setLan(int val) {
        prefs.edit().putInt ("language", val).commit();
    }

    public int getLan() {
        int lan = prefs.getInt ("language",0);
        return lan;
    }

    public void setRegister(int val) {
        prefs.edit().putInt ("register", val).commit();
    }

    public int getRegister() {
        int lan = prefs.getInt ("register",0);
        return lan;
    }

    public void setPay(int val) {
        prefs.edit().putInt ("payment", val).commit();
    }

    public int getPay() {
        int lan = prefs.getInt ("payment",0);
        return lan;
    }
}
