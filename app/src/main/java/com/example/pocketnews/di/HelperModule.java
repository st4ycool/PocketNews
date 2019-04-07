package com.example.pocketnews.di;

import android.app.Application;

import com.example.pocketnews.data.AppDataManager;
import com.example.pocketnews.data.DataManager;
import com.example.pocketnews.data.db.AppDbHelper;
import com.example.pocketnews.data.db.DbHelper;
import com.example.pocketnews.data.network.ApiHelper;
import com.example.pocketnews.data.network.NewsApiHelper;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module (includes = NetModule.class)
public class HelperModule {

    public HelperModule() {
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(Retrofit retrofit) {
        return new NewsApiHelper(retrofit);
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(Application application) {
        return new AppDbHelper(application);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(ApiHelper apiHelper, DbHelper dbHelper) {
        return new AppDataManager(apiHelper, dbHelper);
    }
}
