package com.example.pocketnews.data.db;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.data.model.Channel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppDbHelper implements DbHelper {

    private PocketNewsRoomDatabase db;

    @Inject
    public AppDbHelper(Application application) {
        db = Room.databaseBuilder(application.getApplicationContext(), PocketNewsRoomDatabase.class, "channels")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void insertAll(List<Channel> channels) {
        db.channelDao().insertAll(channels);
    }

    @Override
    public void insertFav(Channel channel) {
        db.channelDao().insertFav(channel);
    }

    @Override
    public Single<List<Channel>> getAllFromDb() {
       return db.channelDao().getAllChannels();
    }

    @Override
    public Single<Channel> getOneFromDb(String name) {
        return db.channelDao().getChannelByName(name);
    }

    @Override
    public Single<List<Channel>> getFavouritesFromDb() {
        return db.channelDao().getFavourites();
    }

    @Override
    public void insertArticles(List<Article> articles) {
        db.articleDao().insertArticles(articles);
    }

    @Override
    public Single<List<Article>> getArticlesForChannel(String sourceName) {
        return db.articleDao().getArticlesForChannel(sourceName);
    }

    @Override
    public Single<List<Article>> getArticlesFromDb() {
        return db.articleDao().getArticlesFromDb();
    }


}
