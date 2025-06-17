package com.deadend.killmyapps;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(this, Database.class, "database").build();
    }
}
