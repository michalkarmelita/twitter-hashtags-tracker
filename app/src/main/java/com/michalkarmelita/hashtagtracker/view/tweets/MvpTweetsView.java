package com.michalkarmelita.hashtagtracker.view.tweets;

import java.util.List;

public interface MvpTweetsView {

    void showSearchResults(List<BaseAdapterItem> items);

}
