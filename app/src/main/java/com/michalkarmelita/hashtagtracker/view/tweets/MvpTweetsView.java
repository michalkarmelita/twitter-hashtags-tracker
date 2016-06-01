package com.michalkarmelita.hashtagtracker.view.tweets;

import com.michalkarmelita.hashtagtracker.model.adaptermodels.TweetAdapterItem;

import java.util.List;

public interface MvpTweetsView {

    void addSearchResults(List<TweetAdapterItem> items);

    void allSearchResultsLoaded();

    void clearSearchResults();

    void showError();

    void toManyRequests();
}
