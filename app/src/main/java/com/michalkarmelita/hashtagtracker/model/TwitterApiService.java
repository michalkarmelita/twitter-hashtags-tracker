package com.michalkarmelita.hashtagtracker.model;

import com.michalkarmelita.hashtagtracker.model.apimodels.TwitterSearchResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterApiService {

    @GET("search/tweets.json")
    Observable<Response<TwitterSearchResponse>> searchForHashtag(@Query("q") String hashtag);

}
