package com.michalkarmelita.hashtagtracker.dagger.tweets;

import com.michalkarmelita.hashtagtracker.dagger.ActivityModule;
import com.michalkarmelita.hashtagtracker.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.hashtagtracker.dagger.AppComponent;
import com.michalkarmelita.hashtagtracker.dagger.NetworkModule;
import com.michalkarmelita.hashtagtracker.view.tweets.TweetsActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class,
                NetworkModule.class,
                TweetsActivityModule.class
        }
)
public interface TweetsActivityComponent {

    void inject(TweetsActivity activity);

}
