package com.example.pocketnews.ui.fragments.Favourites.News;

import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.data.model.Channel;
import com.example.pocketnews.data.network.JSONResponseArticles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenterImpl implements NewsPresenter {

    private NewsFragmentView newsFragmentView;
    private DataManager dataManager;
    private List<Article> articles = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();


    public NewsPresenterImpl(NewsFragmentView newsFragmentView, DataManager dataManager) {
        this.newsFragmentView = newsFragmentView;
        this.dataManager = dataManager;
    }

    @Override
    public void onCreate() {        //todo: refactor all rxjava here

        newsFragmentView.startRefreshing();

        disposables.add(dataManager.getArticlesFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Article>>() {

                                   @Override
                                   public void onSuccess(List<Article> articlesDb) {
                                       newsFragmentView.showNews((ArrayList<Article>) articlesDb);
                                   }

                                   @Override
                                   public void onError(Throwable e) {

                                   }
                               }));

        disposables.add(dataManager.getFavouritesFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Channel>>() {
                    @Override
                    public void onSuccess(List<Channel> channels) {
                        for (Channel channel : channels) {
                            disposables.add(dataManager.searchChannelsInWeb(channel.getName())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableSingleObserver<JSONResponseArticles>() {
                                        @Override
                                        public void onSuccess(JSONResponseArticles jsonResponseArticles) {
                                            newsFragmentView.stopRefreshing();
                                            articles.addAll(Arrays.asList(jsonResponseArticles.getArticles()));
                                            dataManager.insertArticles(articles);
                                            newsFragmentView.showNews((ArrayList<Article>) articles);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            newsFragmentView.stopRefreshing();
                                            newsFragmentView.showToast("No internet connection :(");
                                        }
                                    }));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsFragmentView.stopRefreshing();
                        newsFragmentView.showToast("No internet connection :(");
                        //todo
                    }
                }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        newsFragmentView = null;
    }
}
