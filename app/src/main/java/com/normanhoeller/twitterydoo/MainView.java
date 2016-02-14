package com.normanhoeller.twitterydoo;

/**
 * Created by norman on 04/02/16.
 * contract the {@link MainActivity} has to fulfil
 */
public interface MainView {

    void navigateToPictureActivity(String searchQuery);
    // used to show while authentication is being done - not implemented yet
    void showLoadingAnimation();
    void hideLoadingAnimation();

}
