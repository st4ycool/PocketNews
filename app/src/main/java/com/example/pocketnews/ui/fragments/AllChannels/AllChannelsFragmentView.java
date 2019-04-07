package com.example.pocketnews.ui.fragments.AllChannels;

import com.example.pocketnews.data.model.Channel;

import java.util.ArrayList;

public interface AllChannelsFragmentView {

    void startRefreshing();
    void stopRefreshing();
    void showChannels(ArrayList<Channel> channels);
    void showToast(String text);
}