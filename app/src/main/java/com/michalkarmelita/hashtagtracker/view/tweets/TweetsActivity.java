package com.michalkarmelita.hashtagtracker.view.tweets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.michalkarmelita.hashtagtracker.App;
import com.michalkarmelita.hashtagtracker.R;
import com.michalkarmelita.hashtagtracker.dagger.ActivityModule;
import com.michalkarmelita.hashtagtracker.dagger.NetworkModule;
import com.michalkarmelita.hashtagtracker.dagger.tweets.DaggerTweetsActivityComponent;
import com.michalkarmelita.hashtagtracker.dagger.tweets.TweetsActivityComponent;
import com.michalkarmelita.hashtagtracker.dagger.tweets.TweetsActivityModule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tweets_list)
    RecyclerView contentRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        setSupportActionBar(toolbar);
        createComponent();
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void createComponent() {
        final TweetsActivityComponent component = DaggerTweetsActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .activityModule(new ActivityModule(this))
                .tweetsActivityModule(new TweetsActivityModule(this))
                .networkModule(new NetworkModule())
                .build();
        component.inject(this);
    }

}
