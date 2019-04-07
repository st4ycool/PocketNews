package com.example.pocketnews.ui.fragments.Favourites.News;


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
import android.widget.Toast;

import com.example.pocketnews.R;
import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.model.Article;
import com.example.pocketnews.di.AppModule;
import com.example.pocketnews.di.DaggerDataManagerComponent;
import com.example.pocketnews.di.DataManagerComponent;
import com.example.pocketnews.di.HelperModule;
import com.example.pocketnews.di.NetModule;
import java.util.ArrayList;

import javax.inject.Inject;

public class NewsFragment extends Fragment implements NewsFragmentView {

    private RecyclerView recyclerView;
    private NewsAdapter adapter = new NewsAdapter();
    private NewsPresenter presenter;
    @Inject DataManager dataManager;
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

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            presenter.onCreate();
        });

        presenter = new NewsPresenterImpl(this, dataManager);
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
    public void showNews(ArrayList<Article> articles) {
        adapter.setData(articles);
    }


    @Override
    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
