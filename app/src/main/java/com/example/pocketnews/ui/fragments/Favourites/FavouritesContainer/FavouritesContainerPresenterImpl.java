package com.example.pocketnews.ui.fragments.Favourites.FavouritesContainer;

public class FavouritesContainerPresenterImpl implements FavouritesContainerPresenter {

    private FavouritesContainerView favouritesBaseView;
    private static int count = 0;

 public FavouritesContainerPresenterImpl(FavouritesContainerView favouritesBaseView) {
     this.favouritesBaseView = favouritesBaseView;
 }

    @Override
    public void onCreate() {
        onToggleButtonClick();
    }

    @Override
    public void onToggleButtonClick() {
        switch (count % 2) {
            case 0:
                favouritesBaseView.showNewsFragment();
                break;
            case 1:
                favouritesBaseView.showChannelsFragment();
                break;
        }
        count++;
    }

    @Override
    public void onDestroy() {

    }

    public int getCount(){
        return count;
    }
}
