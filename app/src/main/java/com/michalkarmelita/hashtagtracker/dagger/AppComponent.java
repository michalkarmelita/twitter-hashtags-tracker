package com.michalkarmelita.hashtagtracker.dagger;

import com.michalkarmelita.hashtagtracker.App;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {

    void inject(App app);

}
