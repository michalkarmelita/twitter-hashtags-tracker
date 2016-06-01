package com.michalkarmelita.hashtagtracker.model.tweets;

import android.support.annotation.NonNull;

import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.hashtagtracker.model.TwitterApiService;
import com.michalkarmelita.hashtagtracker.model.apimodels.TwitterSearchResponse;

import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class TweetsDataProvider implements MvpTweetsModel {

    private static final int BAD_REQUEST_CODE = 404;
    private static final int TOO_MANY_REQUEST_CODE = 429;

    private final PublishSubject<String> searchQuerySubject = PublishSubject.create();
    private final ReplaySubject<String> nextUrlSubject = ReplaySubject.create();
    private final PublishSubject<String> loadMoreSubject = PublishSubject.create();
    private final Observable<TwitterSearchResponse> twitterSearchResultObservable;
    private final Observable<Object> twitterSearchErrorObservable;
    private final Observable<Object> noMoreTweetsObservable;
    private final Observable<Object> toManyRequestsErrorObservable;

    public TweetsDataProvider(final TwitterApiService apiService,
                              @NetworkScheduler final Scheduler networkScheduler) {

        final Observable<Response<TwitterSearchResponse>> responseObservable = searchQuerySubject
                .switchMap(new Func1<String, Observable<Response<TwitterSearchResponse>>>() {
                    @Override
                    public Observable<Response<TwitterSearchResponse>> call(String query) {
                        return apiService.searchForHashtag(query)
                                .subscribeOn(networkScheduler);
                    }
                });

        final Observable<Response<TwitterSearchResponse>> loadMoreResultObservable = nextUrlSubject
                .sample(loadMoreSubject)
                .switchMap(new Func1<String, Observable<Response<TwitterSearchResponse>>>() {
                    @Override
                    public Observable<Response<TwitterSearchResponse>> call(String nextResultsUrl) {
                        return apiService.getNextResults(nextResultsUrl)
                                .subscribeOn(networkScheduler);
                    }
                });

        twitterSearchResultObservable = Observable.merge(responseObservable, loadMoreResultObservable)
                .filter(new Func1<Response<TwitterSearchResponse>, Boolean>() {
                    @Override
                    public Boolean call(Response<TwitterSearchResponse> retrofitResponse) {
                        return retrofitResponse.isSuccessful();
                    }
                })
                .map(new Func1<Response<TwitterSearchResponse>, TwitterSearchResponse>() {
                    @Override
                    public TwitterSearchResponse call(Response<TwitterSearchResponse> retrofitResponse) {
                        return retrofitResponse.body();
                    }
                })
                .doOnNext(new Action1<TwitterSearchResponse>() {
                    @Override
                    public void call(TwitterSearchResponse twitterSearchResponse) {
                        nextUrlSubject.onNext("search/tweets.json" + twitterSearchResponse.getSearchMetadata().getNextResults());
                    }
                });

        noMoreTweetsObservable = loadMoreResultObservable
                .filter(new Func1<Response<TwitterSearchResponse>, Boolean>() {
                    @Override
                    public Boolean call(Response<TwitterSearchResponse> retrofitResponse) {
                        return retrofitResponse.code() == BAD_REQUEST_CODE;
                    }
                })
                .map(mapToNull());

        toManyRequestsErrorObservable = Observable.merge(responseObservable, loadMoreResultObservable)
                .filter(new Func1<Response<TwitterSearchResponse>, Boolean>() {
                    @Override
                    public Boolean call(Response<TwitterSearchResponse> retrofitResponse) {
                        return retrofitResponse.code() == TOO_MANY_REQUEST_CODE;
                    }
                })
                .map(mapToNull());

        twitterSearchErrorObservable = Observable.merge(responseObservable, loadMoreResultObservable)
                .filter(new Func1<Response<TwitterSearchResponse>, Boolean>() {
                    @Override
                    public Boolean call(Response<TwitterSearchResponse> retrofitResponse) {
                        return !retrofitResponse.isSuccessful() && retrofitResponse.code() != BAD_REQUEST_CODE
                                && retrofitResponse.code() != TOO_MANY_REQUEST_CODE;
                    }
                })
        .map(mapToNull());
    }

    @NonNull
    private Func1<Response<TwitterSearchResponse>, Object> mapToNull() {
        return new Func1<Response<TwitterSearchResponse>, Object>() {
            @Override
            public Object call(Response<TwitterSearchResponse> twitterSearchResponseResponse) {
                return null;
            }
        };
    }

    @Override
    public void newQuery(String query) {
        searchQuerySubject.onNext(query);
    }

    @Override
    public void loadMore() {
        loadMoreSubject.onNext(null);
    }

    @Override
    public Observable<TwitterSearchResponse> getSearchResultsObservable() {
        return twitterSearchResultObservable;
    }

    @Override
    public Observable<Object> getAllObjectsLoadedObservable() {
        return noMoreTweetsObservable;
    }

    @Override
    public Observable<Object> tooManyRequestsObservable() {
        return toManyRequestsErrorObservable;
    }

    @Override
    public Observable<Object> getErrorObservable() {
        return twitterSearchErrorObservable;
    }
}
