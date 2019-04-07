package com.example.pocketnews.ui.fragments.Favourites.News;

import com.example.pocketnews.data.model.Article;
import java.util.ArrayList;

public interface NewsFragmentView {

    void startRefreshing();
    void stopRefreshing();
    void showNews (ArrayList<Article> articles);
    void showToast(String text);
}
