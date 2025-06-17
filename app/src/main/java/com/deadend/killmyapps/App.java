package com.deadend.killmyapps;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

public class App extends Application {

    public static final String THEME_MODE = "themeMode";
    public static final String LIST_MODE = "listMode";
    public static final String HIDE_KILL_MY_APPS = "hideKillMyApps";
    public static final String HIDE_DEFAULT_LAUNCHER = "hideDefaultLauncher";
    public static final String HIDE_SYSTEM_UI = "hideSystemUI";

    public static Database database;
    public static SharedPreferences settings;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, Database.class, "database").build();
        settings = getSharedPreferences("settings", MODE_PRIVATE);
    }
}
