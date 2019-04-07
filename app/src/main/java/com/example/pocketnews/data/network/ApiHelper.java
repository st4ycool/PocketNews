package com.example.pocketnews.data.network;

import io.reactivex.Single;

public interface ApiHelper {
    Single<JSONResponseAllChannels> receiveChannelsListFromWeb();
    Single<JSONResponseArticles> searchChannelsInWeb(String query);
}
