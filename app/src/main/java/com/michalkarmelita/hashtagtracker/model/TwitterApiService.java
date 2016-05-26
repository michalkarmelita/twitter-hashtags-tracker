package com.michalkarmelita.hashtagtracker.model;

import com.michalkarmelita.hashtagtracker.model.api.TwitterData;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterApiService {

    @GET("search/tweets.json")
    Observable<Response<TwitterData>> searchForHashtag(@Query("q") String hashtag);

}
