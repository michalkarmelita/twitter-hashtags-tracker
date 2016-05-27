package com.michalkarmelita.hashtagtracker.model.tweets;

import com.michalkarmelita.hashtagtracker.model.TwitterApiService;
import com.michalkarmelita.hashtagtracker.model.apimodels.TwitterSearchResponse;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class TweetsDataProvider implements MvpTweetsModel {

    private final PublishSubject<String> searchQuerySubject = PublishSubject.create();
    private final Observable<TwitterSearchResponse> twitterSearchResultObservable;
    private final Observable<Object> twitterSearchErrorObservable;

    @Inject
    public TweetsDataProvider(final TwitterApiService apiService) {

        final Observable<Response<TwitterSearchResponse>> responseObservable = searchQuerySubject
                .switchMap(new Func1<String, Observable<Response<TwitterSearchResponse>>>() {
                    @Override
                    public Observable<Response<TwitterSearchResponse>> call(String query) {
                        return apiService.searchForHashtag(query);
                    }
                });

        twitterSearchResultObservable = responseObservable
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
                });

        twitterSearchErrorObservable = responseObservable
                .filter(new Func1<Response<TwitterSearchResponse>, Boolean>() {
                    @Override
                    public Boolean call(Response<TwitterSearchResponse> retrofitResponse) {
                        return !retrofitResponse.isSuccessful();
                    }
                })
        .map(new Func1<Response<TwitterSearchResponse>, Object>() {
            @Override
            public Object call(Response<TwitterSearchResponse> twitterSearchResponseResponse) {
                return null;
            }
        });
    }

    @Override
    public void setQuery(String query) {
        searchQuerySubject.onNext(query);
    }

    @Override
    public Observable<TwitterSearchResponse> getSearchResultsObservable() {
        return twitterSearchResultObservable;
    }

    @Override
    public Observable<Object> getErrorObservable() {
        return twitterSearchErrorObservable;
    }
}
