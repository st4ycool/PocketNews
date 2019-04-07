package com.example.pocketnews.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.example.pocketnews.R;
import com.example.pocketnews.ui.fragments.AllChannels.AllChannelsFragment;
import com.example.pocketnews.ui.fragments.Favourites.FavouritesContainer.FavouritesContainer;
import com.example.pocketnews.ui.fragments.Search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MainActivityAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MainActivityAdapter(getSupportFragmentManager());

        bindViews();
        setupViews();
    }

    private void bindViews() {
        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
    }

    private void setupViews() {
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        AllChannelsFragment fragmentAll = (AllChannelsFragment) adapter.getItem(position);
                        fragmentAll.onResume();
                        break;

                    case 1:
                        FavouritesContainer fragmentFavs = (FavouritesContainer) adapter.getItem(position);
                        if (fragmentFavs.getCount()%2 == 0)
                            fragmentFavs.showChannelsFragment();
                        else
                            fragmentFavs.showNewsFragment();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new AllChannelsFragment(), getString(R.string.tab_all_ch_text));
        adapter.addFragment(new FavouritesContainer(), getString(R.string.tab_favourites_text));
        adapter.addFragment(new SearchFragment(), getString(R.string.tab_search_text));
        viewPager.setAdapter(adapter);
    }
}

