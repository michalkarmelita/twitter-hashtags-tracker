package com.michalkarmelita.hashtagtracker;

import android.app.Application;

import com.michalkarmelita.hashtagtracker.dagger.AppComponent;
import com.michalkarmelita.hashtagtracker.dagger.AppModule;
import com.michalkarmelita.hashtagtracker.dagger.DaggerAppComponent;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDaggerGraph();
    }

    private void setupDaggerGraph() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public static AppComponent getAppComponent(Application app) {
        return ((App) app).appComponent;
    }

}
