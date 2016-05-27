package com.michalkarmelita.hashtagtracker.presenter.tweets;

public interface MvpTweetsPresenter {

    void searchQuery(String query);

    void onViewDestroyed();

}
