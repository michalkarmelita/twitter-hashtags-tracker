package com.michalkarmelita.hashtagtracker.dagger;

import com.michalkarmelita.hashtagtracker.App;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.UiScheduler;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {

    void inject(App app);

    @NetworkScheduler
    Scheduler getNetworkScheduler();

    @UiScheduler
    Scheduler getUiScheduler();

}
