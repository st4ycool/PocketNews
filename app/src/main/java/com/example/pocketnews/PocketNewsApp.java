package com.example.pocketnews;

import android.app.Application;
import android.content.Context;
import com.example.pocketnews.di.AppModule;
import com.example.pocketnews.di.DaggerDataManagerComponent;
import com.example.pocketnews.di.DataManagerComponent;
import com.example.pocketnews.di.HelperModule;
import com.example.pocketnews.di.NetModule;


public class PocketNewsApp extends Application {

    private DataManagerComponent dataManagerComponent;

    public static PocketNewsApp get(Context context){
        return (PocketNewsApp)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dataManagerComponent = DaggerDataManagerComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://newsapi.org"))
                .helperModule(new HelperModule())
                .build();
        dataManagerComponent.inject(this);
    }

   public DataManagerComponent getDataManagerComponent() {
        return dataManagerComponent;
    }

   public void setDataManagerComponent(DataManagerComponent dataManagerComponent) {
        this.dataManagerComponent = dataManagerComponent;
    }
}