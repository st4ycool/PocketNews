package com.example.pocketnews.ui.fragments.Search;

public interface SearchPresenter {

    void onCreate();
    void onSearchClick(String query);
    void onDestroy();
}
