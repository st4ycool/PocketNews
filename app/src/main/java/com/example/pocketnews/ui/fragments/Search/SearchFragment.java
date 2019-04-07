package com.example.pocketnews.ui.fragments.Search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

public class SearchFragment extends Fragment implements SearchFragmentView, SearchView.OnQueryTextListener {

    private ProgressBar pBAR;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter adapter = new SearchAdapter();
    private SearchPresenter presenter;
    @Inject DataManager dataManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadComponent();
        setupUI(view);

        presenter = new SearchPresenterImpl(this, dataManager);
        searchView.setOnQueryTextListener(this);
    }

        private void loadComponent(){
            DataManagerComponent component = DaggerDataManagerComponent.builder()
                    .appModule(new AppModule(getActivity().getApplication()))
                    .netModule(new NetModule("https://newsapi.org"))
                    .helperModule(new HelperModule())
                    .build();
            component.inject(this);
        }


    private void setupUI (View view){
        searchView = view.findViewById(R.id.search_view);
        pBAR = view.findViewById(R.id.progress_search);
        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void onCreate () {
        presenter.onCreate();
    }

    @Override
    public void startRefreshing () {
        pBAR.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopRefreshing () {
        pBAR.setVisibility(View.GONE);
    }

    @Override
    public void showChannels(ArrayList<Article> articles) {
        adapter.setData(articles);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy () {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.onSearchClick(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}