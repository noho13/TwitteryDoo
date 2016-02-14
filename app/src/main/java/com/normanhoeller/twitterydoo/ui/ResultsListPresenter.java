package com.normanhoeller.twitterydoo.ui;

/**
 * Created by norman on 05/02/16.
 */
public interface ResultsListPresenter {

    void queryTwitter(String searchQuery);
    void onResume();
    void onDestroy();
    void setToolbarTitle(String toolbarTitle);
}
