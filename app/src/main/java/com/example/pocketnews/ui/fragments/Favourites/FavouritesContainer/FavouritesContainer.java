package com.example.pocketnews.ui.fragments.Favourites.FavouritesContainer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.pocketnews.R;
import com.example.pocketnews.ui.fragments.Favourites.FavChannels.FavChannelsFragment;
import com.example.pocketnews.ui.fragments.Favourites.News.NewsFragment;

public class FavouritesContainer extends Fragment implements FavouritesContainerView {

    public Switch toggleSwitch;
    FavouritesContainerPresenterImpl presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new FavouritesContainerPresenterImpl(this);

        toggleSwitch = view.findViewById(R.id.replace_button);
        toggleSwitch.setOnClickListener(v -> presenter.onToggleButtonClick());
    }

    @Override
    public void showChannelsFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_base_favourites, new FavChannelsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showNewsFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_base_favourites, new NewsFragment())
                .addToBackStack(null)
                .commit();
    }

    public int getCount() {
        return presenter.getCount();
    }
}

