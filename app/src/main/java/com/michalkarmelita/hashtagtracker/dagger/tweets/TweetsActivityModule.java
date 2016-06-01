package com.michalkarmelita.hashtagtracker.dagger.tweets;

import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.UiScheduler;
import com.michalkarmelita.hashtagtracker.model.TwitterApiService;
import com.michalkarmelita.hashtagtracker.model.tweets.MvpTweetsModel;
import com.michalkarmelita.hashtagtracker.model.tweets.TweetsDataProvider;
import com.michalkarmelita.hashtagtracker.presenter.tweets.MvpTweetsPresenter;
import com.michalkarmelita.hashtagtracker.presenter.tweets.TweetsPresenter;
import com.michalkarmelita.hashtagtracker.view.tweets.MvpTweetsView;
import com.michalkarmelita.hashtagtracker.view.tweets.TweetsActivity;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class TweetsActivityModule {

    private final MvpTweetsView view;

    public TweetsActivityModule(MvpTweetsView activity) {
        this.view = activity;
    }

    @Provides
    MvpTweetsModel provideTweetsModel(TwitterApiService apiService, @NetworkScheduler Scheduler networkScheduler) {
        return new TweetsDataProvider(apiService, networkScheduler);
    }

    @Provides
    MvpTweetsPresenter provideTweetsPresenter(MvpTweetsModel tweetsModel, @UiScheduler Scheduler uiScheduler) {
        return new TweetsPresenter(tweetsModel, view, uiScheduler);
    }

    @Provides
    MvpTweetsView provideMvpTweetsView() {
        return view;
    }

}
