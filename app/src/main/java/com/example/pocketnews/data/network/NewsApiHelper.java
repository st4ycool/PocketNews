package com.example.pocketnews.data.network;

import com.example.pocketnews.data.network.connectionCheck.NoConnectivityException;

import javax.inject.Inject;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class NewsApiHelper implements ApiHelper {

    private final String API_KEY = "f45d2458ca244f83a37094709d4d38e1";
    @Inject Retrofit retrofit;

    public NewsApiHelper(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public Single<JSONResponseAllChannels> receiveChannelsListFromWeb() {
        Requests.RequestAllChannels request = retrofit.create(Requests.RequestAllChannels.class);
        return request.getChannelsList(API_KEY);
    }

    public Single<JSONResponseArticles> searchChannelsInWeb(String query){
            Requests.RequestSearch request = retrofit.create(Requests.RequestSearch.class);
        return request.findChannels(query, API_KEY);
    }
}