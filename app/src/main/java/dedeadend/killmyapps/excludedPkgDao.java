package dedeadend.killmyapps;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dedeadend.killmyapps.model.PKGName;

import java.util.List;

@Dao
public interface excludedPkgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PKGName pkgName);

    @Delete
    void delete(PKGName pkgName);

    @Query("SELECT * FROM excludedPkg")
    List<PKGName> getAll();
}
