package com.example.pocketnews.di;

import android.app.Application;

import com.example.pocketnews.PocketNewsApp;
import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.ui.fragments.AllChannels.AllChannelsFragment;
import com.example.pocketnews.ui.fragments.Favourites.FavChannels.FavChannelsFragment;
import com.example.pocketnews.ui.fragments.Favourites.News.NewsFragment;
import com.example.pocketnews.ui.fragments.Search.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, HelperModule.class})
public interface DataManagerComponent {

    DataManager dataManager();
    Retrofit retrofit();
    Application application();

    void inject(AllChannelsFragment fragment);
    void inject(FavChannelsFragment fragment);
    void inject(NewsFragment fragment);
    void inject(SearchFragment fragment);
    void inject(PocketNewsApp application);
}

