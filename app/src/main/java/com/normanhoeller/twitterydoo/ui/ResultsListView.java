package com.normanhoeller.twitterydoo.ui;

import android.view.View;

import com.normanhoeller.twitterydoo.model.ViewModelResult;

import java.util.List;

/**
 * Created by norman on 05/02/16.
 */
public interface ResultsListView {

    void showLoadMoreProgressView();
    void hideProgressView();
    void setToolbarTitle(String toolbarTitle);

    void loadMoreTweets();
    void queryTwitter(String query);
    void showSnackBar(View view);
    void setResult(List<ViewModelResult> searchResult);


}
