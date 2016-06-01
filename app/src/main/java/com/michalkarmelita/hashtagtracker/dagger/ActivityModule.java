package com.michalkarmelita.hashtagtracker.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.ForActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ForActivity
    public Resources provideResources() {
        return activity.getResources();
    }

    @Provides
    @ForActivity
    public Context provideContext() {
        return activity;
    }

    @Provides
    public LayoutInflater provideLayoutInflater(@ForActivity Context context) {
        return LayoutInflater.from(context);
    }

}