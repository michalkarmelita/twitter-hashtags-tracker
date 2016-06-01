package com.michalkarmelita.hashtagtracker.presenter.tweets;

import com.michalkarmelita.hashtagtracker.model.tweets.MvpTweetsModel;
import com.michalkarmelita.hashtagtracker.view.tweets.MvpTweetsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import rx.Scheduler;

import static org.junit.Assert.*;

public class TweetsPresenterTest {


    TweetsPresenter presenter;

    @Mock
    MvpTweetsModel model;
    @Mock
    MvpTweetsView view;
    @Mock
    Scheduler scheduler;

    @Before
    public void setUp() throws Exception {
        presenter = new TweetsPresenter(model, view, scheduler);
    }

    @Test
    public void testConvertionToHashtagIsCorrect() throws Exception {

        String query = "weather";

        String result = presenter.convertToHashtag(query);

        assertTrue(result.equals("#" + query));

    }
}