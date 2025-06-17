package com.deadend.killmyapps;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.deadend.killmyapps.model.PKGName;

import java.util.List;

@Dao
public interface excludedPkgDao {
    @Insert
    void insert(PKGName pkgName);
    @Delete
    void delete(PKGName pkgName);
    @Query("SELECT * FROM excludedPkg")
    List<PKGName> getAll();
}
