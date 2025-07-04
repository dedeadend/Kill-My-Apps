package com.deadend.killmyapps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

public class App extends Application {

    public static final String THEME_MODE = "themeMode";
    public static final String LIST_MODE = "listMode";
    public static final String HIDE_KILL_MY_APPS = "hideKillMyApps";
    public static final String HIDE_DEFAULT_LAUNCHER = "hideDefaultLauncher";
    public static final String HIDE_SYSTEM_UI = "hideSystemUI";
    public static final String SHOW_PKGNAME = "showPkgName";
    public static final String CLICK_TO_APP_INFO = "clickToAppInfo";
    public static final String LONG_CLICK_TO_COPY = "longClickToCopy";

    public static Database database;
    public static SharedPreferences settings;
    public static Context context;
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, Database.class, "database").build();
        settings = getSharedPreferences("settings", MODE_PRIVATE);
        context = getApplicationContext();
        handler = new Handler(Looper.getMainLooper());
    }

    public static void setAppThemeMode() {
        int themeMode = App.settings.getInt(App.THEME_MODE, 0);
        if (themeMode == 0)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        else if (themeMode == 1)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if (themeMode == 2)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public static void toast(String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
