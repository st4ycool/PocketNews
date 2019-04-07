package com.example.pocketnews.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.example.pocketnews.data.model.Channel;
import java.util.List;
import io.reactivex.Single;

@Dao
public interface ChannelDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    void insertAll(List<Channel> channels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFav(Channel channel);

    @Query("SELECT * from Channel ORDER BY name ASC")
    Single<List<Channel>> getAllChannels();

    @Query("SELECT * from Channel WHERE isFavourite LIKE 1 ORDER BY name ASC")
    Single<List<Channel>> getFavourites();

    @Query("SELECT * from Channel WHERE name LIKE :name")
    Single<Channel> getChannelByName(String name);
}