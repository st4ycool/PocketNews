package com.example.pocketnews.data.db;

import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.data.model.Channel;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;

public interface DbHelper {

    void insertAll(List<Channel> channels);
    void insertFav(Channel channel);
    Single<List<Channel>> getAllFromDb();
    Single<Channel> getOneFromDb(String name);
    Single<List<Channel>> getFavouritesFromDb();


    void insertArticles(List<Article> articles);
    Single<List<Article>> getArticlesForChannel(String sourceName);

    Single<List<Article>> getArticlesFromDb();
}
