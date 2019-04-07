package com.example.pocketnews.data.network;

import com.example.pocketnews.data.model.Channel;

public class JSONResponseAllChannels {
    private String status;
    private Channel[] sources;

    public Channel[] getChannels() {
        return sources;
    }
}
