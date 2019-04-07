package com.example.pocketnews.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.pocketnews.data.model.Article;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<Article> articles);

    @Query("SELECT * from Article WHERE source_name LIKE :name")
    Single<List<Article>> getArticlesForChannel(String name);

    @Query("SELECT * from Article")
    Single<List<Article>> getArticlesFromDb();
}
