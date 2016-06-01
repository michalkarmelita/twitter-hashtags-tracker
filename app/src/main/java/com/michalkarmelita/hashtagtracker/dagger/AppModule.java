package com.michalkarmelita.hashtagtracker.dagger;


import android.content.Context;

import com.google.gson.Gson;
import com.michalkarmelita.hashtagtracker.App;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.ForApplication;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.UiScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApplication
    public Context appContext() {
        return app.getApplicationContext();
    }

    @Provides
    @NetworkScheduler
    Scheduler provideNetworkScheduler(){
        return Schedulers.io();
    }

    @Provides
    @UiScheduler
    Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }
}
