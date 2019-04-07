package com.example.pocketnews.ui.fragments.AllChannels;

import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.model.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AllChannelsPresenterImpl implements AllChannelsPresenter {

    public static final int TRUE = 1;
    public static final int FALSE = 0;
    private AllChannelsFragmentView channelsListFragmentView;
    private DataManager dataManager;
    private CompositeDisposable disposables = new CompositeDisposable();


    public AllChannelsPresenterImpl(AllChannelsFragmentView allChannelsFragmentView, DataManager dataManager) {
        this.channelsListFragmentView = allChannelsFragmentView;
        this.dataManager = dataManager;
    }

    @Override
    public void onCreate() {

        channelsListFragmentView.startRefreshing();       // get all from web + insert to db and then getAll from DB and show


        disposables.add(
                dataManager.receiveChannelsListFromWeb()
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess((jsonResponseAllChannels) -> dataManager.insertAll(Arrays.asList(jsonResponseAllChannels.getChannels())))
                        .flatMap((apiResponse) -> dataManager.getAllFromDb())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((channels) -> {
                            channelsListFragmentView.showChannels((ArrayList<Channel>) channels);
                            channelsListFragmentView.stopRefreshing();
                        }, throwable -> {
                            channelsListFragmentView.stopRefreshing();
                            channelsListFragmentView.showToast("No internet connection :(");
                        })
        );
    }

    @Override
    public void onFavouriteClick(String name) {

        disposables.add(dataManager.getOneFromDb(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Channel>() {

                    @Override
                    public void onSuccess(Channel channel) {
                        if (channel.getIsFavourite() == TRUE) {
                            channel.setIsFavourite(FALSE);
                        } else if (channel.getIsFavourite() == FALSE) {
                            channel.setIsFavourite(TRUE);
                        }
                        dataManager.insertFav(channel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //todo
                    }
                }));
    }

    public void onDestroy() {
        disposables.dispose();
        channelsListFragmentView = null;
    }
}
