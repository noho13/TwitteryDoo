package com.normanhoeller.twitterydoo.dagger;


import com.normanhoeller.twitterydoo.DataManager;

/**
 * Created by norman on 02/02/16.
 */
public interface ApplicationComponent {
    void inject(DataManager dataManager);
}
