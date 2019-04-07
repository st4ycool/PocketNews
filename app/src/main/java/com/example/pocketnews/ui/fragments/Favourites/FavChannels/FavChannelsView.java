package com.example.pocketnews.ui.fragments.Favourites.FavChannels;

import com.example.pocketnews.data.model.Channel;

import java.util.ArrayList;

public interface FavChannelsView {

    void startRefreshing();
    void stopRefreshing();
    void showChannels(ArrayList<Channel> channels);
    void showToast(String text);
}


