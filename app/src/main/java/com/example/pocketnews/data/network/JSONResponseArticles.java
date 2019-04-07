package com.example.pocketnews.data.network;

import com.example.pocketnews.data.model.Article;

public class JSONResponseArticles {
    private String status;
    private Article[] articles;

    public Article[] getArticles() {
        return articles;
    }
}
