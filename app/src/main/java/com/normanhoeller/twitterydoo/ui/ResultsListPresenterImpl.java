package com.normanhoeller.twitterydoo.ui;

import com.normanhoeller.twitterydoo.DataManager;
import com.normanhoeller.twitterydoo.model.ViewModelResult;

import java.util.List;

/**
 * Created by norman on 05/02/16.
 */
public class ResultsListPresenterImpl implements ResultsListPresenter, DataManager.Callback {

    private DataManager dataManager;
    private ResultsListView resultsListView;

    public ResultsListPresenterImpl(ResultsListView resultsListView) {
        dataManager = DataManager.getInstance();
        dataManager.setCallback(this);
        this.resultsListView = resultsListView;
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {
        resultsListView.setToolbarTitle(toolbarTitle);
    }

    @Override
    public void queryTwitter(String searchQuery) {
        dataManager.queryTwitterService(searchQuery);
    }


    @Override
    public void setResult(List<ViewModelResult> searchResult) {
        resultsListView.hideProgressView();
        resultsListView.setResult(searchResult);
    }

    @Override
    public void showLoadMoreProgressView() {
        resultsListView.showLoadMoreProgressView();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        dataManager.setCallback(null);
        resultsListView = null;
    }
}
