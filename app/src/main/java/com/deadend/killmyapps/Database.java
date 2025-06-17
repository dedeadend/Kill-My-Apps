package com.deadend.killmyapps;

import androidx.room.RoomDatabase;

import com.deadend.killmyapps.model.PKGName;

@androidx.room.Database(entities = {PKGName.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract excludedPkgDao excludedPkgDao();
}
