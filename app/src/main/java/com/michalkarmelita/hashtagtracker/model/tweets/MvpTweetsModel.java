package com.michalkarmelita.hashtagtracker.model.tweets;

import com.michalkarmelita.hashtagtracker.model.apimodels.TwitterSearchResponse;

import rx.Observable;

public interface MvpTweetsModel {

    void setQuery(String query);

    Observable<TwitterSearchResponse> getSearchResultsObservable();

    Observable<Object> getErrorObservable();

}
