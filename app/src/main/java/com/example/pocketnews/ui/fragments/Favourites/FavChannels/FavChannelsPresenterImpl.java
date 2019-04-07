package com.example.pocketnews.ui.fragments.Favourites.FavChannels;

import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.model.Channel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.pocketnews.ui.fragments.AllChannels.AllChannelsPresenterImpl.FALSE;
import static com.example.pocketnews.ui.fragments.AllChannels.AllChannelsPresenterImpl.TRUE;

public class FavChannelsPresenterImpl implements FavChannelsPresenter {

    private FavChannelsView favouritesFragmentView;
    private DataManager dataManager;
    private CompositeDisposable disposables = new CompositeDisposable();

    public FavChannelsPresenterImpl(FavChannelsView favouritesFragmentView, DataManager dataManager) {
        this.favouritesFragmentView = favouritesFragmentView;
        this.dataManager = dataManager;
    }

    @Override
    public void onCreate() {
        disposables.add(
                dataManager.getFavouritesFromDb()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((channels) -> {
                            favouritesFragmentView.showChannels((ArrayList<Channel>) channels);
                            favouritesFragmentView.stopRefreshing();
                        }, throwable -> {
                            favouritesFragmentView.stopRefreshing();
                            favouritesFragmentView.showToast("No internet connection :(");
                        })
        );
    }

    @Override
    public void onFavouriteClick(String name) {
        disposables.add(
                dataManager.getOneFromDb(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((channel) -> {
                            if (channel.getIsFavourite() == TRUE) {
                                channel.setIsFavourite(FALSE);
                            } else if (channel.getIsFavourite() == FALSE) {
                                channel.setIsFavourite(TRUE);
                            }
                            dataManager.insertFav(channel);
                        })
        );
    }

    public void onDestroy() {
        favouritesFragmentView = null;
        disposables.dispose();
    }
}
