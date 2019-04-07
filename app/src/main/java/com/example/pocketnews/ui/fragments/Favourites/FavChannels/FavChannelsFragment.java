package com.example.pocketnews.ui.fragments.Favourites.FavChannels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pocketnews.R;
import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.model.Channel;
import com.example.pocketnews.di.AppModule;
import com.example.pocketnews.di.DaggerDataManagerComponent;
import com.example.pocketnews.di.DataManagerComponent;
import com.example.pocketnews.di.HelperModule;
import com.example.pocketnews.di.NetModule;
import java.util.ArrayList;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FavChannelsFragment extends Fragment implements FavChannelsView {

    @Inject DataManager dataManager;
    private RecyclerView recyclerView;
    private FavChannelsAdapter adapter = new FavChannelsAdapter();
    private FavChannelsPresenter presenter;
    private CompositeDisposable disposables = new CompositeDisposable();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourites,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadComponent();
        setupUI(view);

        presenter = new FavChannelsPresenterImpl(this, dataManager);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onCreate());

        Observable<String> observableStarBtnCLicks = adapter.getPositionClicks();
        disposables.add(observableStarBtnCLicks
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        presenter.onFavouriteClick(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                }));
        presenter.onCreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onCreate();
    }

    private void loadComponent() {

        DataManagerComponent component = DaggerDataManagerComponent.builder()
                .appModule(new AppModule(getActivity().getApplication()))
                .netModule(new NetModule("https://newsapi.org"))
                .helperModule(new HelperModule())
                .build();
        component.inject(this);
    }

    private void setupUI(View view) {

        recyclerView = view.findViewById(R.id.recycler_view_favourites);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.favourites_swipe_container);
    }

    @Override
    public void startRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showChannels(ArrayList<Channel> channels) {
        adapter.setData(channels);
    }

    @Override
    public void showToast(String text) {
        //todo
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
