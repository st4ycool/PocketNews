package com.example.pocketnews.ui.fragments.Search;

import com.example.pocketnews.data.model.Article;

import java.util.ArrayList;

public interface SearchFragmentView {

    void startRefreshing();
    void stopRefreshing();
    void showChannels(ArrayList<Article> articles);
    void showToast(String text);
}
