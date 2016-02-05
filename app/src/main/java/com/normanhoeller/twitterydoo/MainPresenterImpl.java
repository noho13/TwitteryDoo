package com.normanhoeller.twitterydoo;

/**
 * Created by norman on 04/02/16.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private DataManager dataManager;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.dataManager = DataManager.getInstance();
    }

    @Override
    public boolean checkIfCredentialsAreReady() {
        return dataManager.isAccessTokenAvailable();
    }

    @Override
    public void navigateToPictureActivity(String searchQuery) {
        mainView.navigateToPictureActivity(searchQuery);
    }

    @Override
    public void getAccessTokenFromTwitter() {
        mainView.showLoadingAnimation();
        dataManager.getAccessTokenFromTwitter();
    }

    @Override
    public void storeAccessTokenInSharedPrefs(String accessToken) {
        dataManager.storeAccessTokenInSharedPrefs(accessToken);
        mainView.hideLoadingAnimation();
    }
}
