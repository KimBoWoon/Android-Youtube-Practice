package com.videovillage.application.mainactivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by secret on 10/15/16.
 */
public class MainPresenterTest {
    @Mock
    private MainPresenter mainPresenter;

    @Mock
    private MainContract.View view;

    private MainPresenter mMainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mMainPresenter = new MainPresenter(view);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void choiceYoutubeChannel() throws Exception {

    }

    @Test
    public void isDebuggable() throws Exception {

    }

    @Test
    public void youtubeChannelInsert() throws Exception {

    }

    @Test
    public void getYoutubeSubscribeList() throws Exception {
        when(mainPresenter.getYoutubeSubscribeList()).thenReturn(new HashMap<String, String>());

        mMainPresenter.getYoutubeSubscribeList();

        verify(mainPresenter).getYoutubeSubscribeList();
    }

}