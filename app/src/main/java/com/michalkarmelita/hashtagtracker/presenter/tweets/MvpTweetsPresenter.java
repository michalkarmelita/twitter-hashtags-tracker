package com.michalkarmelita.hashtagtracker.presenter.tweets;

public interface MvpTweetsPresenter {

    void searchQuery(String query);

    void loadMore();

    void onViewDestroyed();

    void searchQueryChanged(String newText);
}
