package com.example.pocketnews.ui.fragments.Search;

import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.data.network.JSONResponseArticles;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchFragmentView searchFragmentView;
    private DataManager dataManager;
    private CompositeDisposable disposables = new CompositeDisposable();
    private ArrayList<Article> articles = new ArrayList<>();

    public SearchPresenterImpl(SearchFragmentView searchFragmentView, DataManager dataManager) {
        this.searchFragmentView = searchFragmentView;
        this.dataManager = dataManager;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onSearchClick(String query) {

        searchFragmentView.startRefreshing();
        disposables.add(dataManager.searchChannelsInWeb(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JSONResponseArticles>() {

                    @Override
                    public void onSuccess(JSONResponseArticles jsonResponseArticles) {
                        articles = new ArrayList<>(Arrays.asList(jsonResponseArticles.getArticles()));
                        searchFragmentView.stopRefreshing();
                        searchFragmentView.showChannels(articles);

                    }

                    @Override
                    public void onError(Throwable e) {
                        searchFragmentView.stopRefreshing();
                        searchFragmentView.showToast("No internet connection :(");
                    }
                }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        searchFragmentView = null;
    }
}
