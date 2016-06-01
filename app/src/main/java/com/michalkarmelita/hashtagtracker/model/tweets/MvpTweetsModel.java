package com.michalkarmelita.hashtagtracker.model.tweets;

import com.michalkarmelita.hashtagtracker.model.apimodels.TwitterSearchResponse;

import rx.Observable;

public interface MvpTweetsModel {

    void newQuery(String query);

    void loadMore();

    Observable<TwitterSearchResponse> getSearchResultsObservable();

    Observable<Object> getAllObjectsLoadedObservable();

    Observable<Object> tooManyRequestsObservable();

    Observable<Object> getErrorObservable();

}
