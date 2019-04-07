package com.example.pocketnews.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.data.model.Channel;


@Database(entities = {Channel.class, Article.class}, version = 2, exportSchema = false)
public abstract class PocketNewsRoomDatabase extends RoomDatabase {

    public abstract ChannelDao channelDao();
    public abstract ArticleDao articleDao();
}
