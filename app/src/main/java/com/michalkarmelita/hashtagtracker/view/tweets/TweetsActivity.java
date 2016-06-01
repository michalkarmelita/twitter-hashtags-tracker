package com.michalkarmelita.hashtagtracker.view.tweets;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.michalkarmelita.hashtagtracker.App;
import com.michalkarmelita.hashtagtracker.R;
import com.michalkarmelita.hashtagtracker.dagger.ActivityModule;
import com.michalkarmelita.hashtagtracker.dagger.NetworkModule;
import com.michalkarmelita.hashtagtracker.dagger.tweets.DaggerTweetsActivityComponent;
import com.michalkarmelita.hashtagtracker.dagger.tweets.TweetsActivityModule;
import com.michalkarmelita.hashtagtracker.model.adaptermodels.TweetAdapterItem;
import com.michalkarmelita.hashtagtracker.presenter.tweets.MvpTweetsPresenter;
import com.michalkarmelita.hashtagtracker.view.tweets.adapter.TweetsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetsActivity extends AppCompatActivity implements MvpTweetsView, TweetsAdapter.OnLoadMoreListener {

    private static final String HASHTAG = "hashtag";
    @BindView(R.id.root_view)
    CoordinatorLayout rootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_contacts)
    SearchView searchView;
    @BindView(R.id.tweets_list)
    RecyclerView contentRecyclerView;

    @Inject
    MvpTweetsPresenter presenter;
    @Inject
    TweetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        createComponent();

        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentRecyclerView.setAdapter(adapter);
        adapter.setupLoadMore(contentRecyclerView);
        adapter.setOnLoadMoreListener(this);

        setupSearch();
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchQueryChanged(newText);
                return false;
            }
        });
    }

    public void createComponent() {
        DaggerTweetsActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .activityModule(new ActivityModule(this))
                .tweetsActivityModule(new TweetsActivityModule(this))
                .networkModule(new NetworkModule())
                .build()
                .inject(this);
    }

    @Override
    public void addSearchResults(List<TweetAdapterItem> items) {
        adapter.addItems(items);
    }

    @Override
    public void allSearchResultsLoaded() {
        adapter.allItemsLoaded();
        Snackbar.make(rootView, R.string.all_items_loaded_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void clearSearchResults() {
        adapter.clearItems();
    }

    @Override
    public void showError() {
        Snackbar.make(rootView, R.string.error_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void toManyRequests() {
        Snackbar.make(rootView, R.string.to_many_requests_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        presenter.loadMore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }
}
