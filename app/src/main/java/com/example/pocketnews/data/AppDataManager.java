package com.example.pocketnews.data;

import com.example.pocketnews.data.db.DbHelper;
import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.data.model.Channel;
import com.example.pocketnews.data.network.ApiHelper;
import com.example.pocketnews.data.network.JSONResponseAllChannels;
import com.example.pocketnews.data.network.JSONResponseArticles;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AppDataManager implements DataManager {

    private ApiHelper apiHelper;
    private DbHelper dbHelper;

    public AppDataManager(ApiHelper apiHelper, DbHelper dbHelper) {
        this.apiHelper = apiHelper;
        this.dbHelper = dbHelper;
    }

    @Override
    public void insertAll(List<Channel> channels) {
        dbHelper.insertAll(channels);
    }

    @Override
    public void insertFav(Channel channel) {
        dbHelper.insertFav(channel);
    }

    @Override
    public Single<List<Channel>> getAllFromDb() {
        return dbHelper.getAllFromDb();
    }

    @Override
    public Single<Channel> getOneFromDb(String name) {
        return dbHelper.getOneFromDb(name);
    }

    @Override
    public Single<List<Channel>> getFavouritesFromDb() {
        return dbHelper.getFavouritesFromDb();
    }

    @Override
    public Single<JSONResponseAllChannels> receiveChannelsListFromWeb() {
        return apiHelper.receiveChannelsListFromWeb();
    }

    @Override
    public Single<JSONResponseArticles> searchChannelsInWeb(String query) {
        return apiHelper.searchChannelsInWeb(query);
    }

    @Override
    public void insertArticles(List<Article> articles) {
        dbHelper.insertArticles(articles);
    }

    @Override
    public Single<List<Article>> getArticlesForChannel(String sourceName) {
        return dbHelper.getArticlesForChannel(sourceName);
    }

    @Override
    public Single<List<Article>> getArticlesFromDb() {
        return dbHelper.getArticlesFromDb();
    }

}