package com.example.pocketnews.data.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface Requests {

    public interface RequestAllChannels {
        @GET("/v2/sources?language=en")
        Single<JSONResponseAllChannels> getChannelsList(@Header("X-api-key") String apiKey);
    }

    public interface RequestSearch {
        @GET("/v2/everything")
            //?sortBy=popularity
        Single<JSONResponseArticles> findChannels(@Query("q") String query, @Header("X-api-key") String apiKey);
    }
}