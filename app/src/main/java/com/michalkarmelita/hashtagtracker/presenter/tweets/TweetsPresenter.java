package com.michalkarmelita.hashtagtracker.presenter.tweets;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.UiScheduler;
import com.michalkarmelita.hashtagtracker.model.adaptermodels.TweetAdapterItem;
import com.michalkarmelita.hashtagtracker.model.apimodels.Status;
import com.michalkarmelita.hashtagtracker.model.apimodels.TwitterSearchResponse;
import com.michalkarmelita.hashtagtracker.model.tweets.MvpTweetsModel;
import com.michalkarmelita.hashtagtracker.view.tweets.MvpTweetsView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class TweetsPresenter implements MvpTweetsPresenter {


    private final MvpTweetsView tweetsView;
    private final MvpTweetsModel tweetsModel;
    private final Scheduler uiScheduler;
    private CompositeSubscription subscription;

    public TweetsPresenter(final MvpTweetsModel tweetsModel,
                           final MvpTweetsView tweetsView,
                           @UiScheduler Scheduler uiScheduler) {
        this.tweetsModel = tweetsModel;
        this.tweetsView = tweetsView;
        this.uiScheduler = uiScheduler;
        subscribe();
    }

    private void subscribe() {
        subscription = new CompositeSubscription(tweetsModel.getSearchResultsObservable()
                .switchMap(new Func1<TwitterSearchResponse, Observable<List<TweetAdapterItem>>>() {
                    @Override
                    public Observable<List<TweetAdapterItem>> call(TwitterSearchResponse twitterSearchResponse) {
                        return Observable.from(twitterSearchResponse.getStatuses())
                                .map(new Func1<Status, TweetAdapterItem>() {
                                    @Override
                                    public TweetAdapterItem call(Status status) {
                                        return new TweetAdapterItem(
                                                status.getText(),
                                                status.getUser().getName(),
                                                status.getUser().getProfileImageUrl()
                                        );
                                    }
                                })
                                .collect(initList(), collector());
                    }
                })
                .observeOn(uiScheduler)
                .subscribe(new Action1<List<TweetAdapterItem>>() {
                    @Override
                    public void call(List<TweetAdapterItem> tweetAdapterItems) {
                        tweetsView.addSearchResults(tweetAdapterItems);
                    }
                }),

                tweetsModel.getAllObjectsLoadedObservable()
                        .observeOn(uiScheduler)
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                tweetsView.allSearchResultsLoaded();
                            }
                        }),

                tweetsModel.tooManyRequestsObservable()
                        .observeOn(uiScheduler)
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                tweetsView.toManyRequests();
                            }
                        }),

                tweetsModel.getErrorObservable()
                        .observeOn(uiScheduler)
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                tweetsView.showError();
                            }
                        })
        );
    }

    private void subscribeIfUnsubscribed() {
        if (subscription == null || subscription.isUnsubscribed()) {
            subscribe();
        }
    }

    private void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @NonNull
    private Func0<List<TweetAdapterItem>> initList() {
        return new Func0<List<TweetAdapterItem>>() {
            @Override
            public List<TweetAdapterItem> call() {
                return new ArrayList<>();
            }
        };
    }

    @NonNull
    private Action2<List<TweetAdapterItem>, TweetAdapterItem> collector() {
        return new Action2<List<TweetAdapterItem>, TweetAdapterItem>() {
            @Override
            public void call(List<TweetAdapterItem> tweetAdapterItems, TweetAdapterItem tweetAdapterItem) {
                tweetAdapterItems.add(tweetAdapterItem);
            }
        };
    }

    @Override
    public void searchQuery(String query) {
        subscribeIfUnsubscribed();
        tweetsModel.newQuery(convertToHashtag(query));
    }

    @Override
    public void loadMore() {
        tweetsModel.loadMore();
    }

    public String convertToHashtag(String queryText) {
        return queryText.startsWith("#") ? queryText : "#" + queryText;
    }

    @Override
    public void onViewDestroyed() {
        unsubscribe();
    }

    @Override
    public void searchQueryChanged(String newText) {
        if (newText.length() == 0) {
            tweetsView.clearSearchResults();
            unsubscribe();
        }
    }
}

