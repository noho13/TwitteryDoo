package com.normanhoeller.twitterydoo.dagger;

import com.normanhoeller.twitterydoo.api.RestClient;
import com.normanhoeller.twitterydoo.api.TwitterService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by norman on 02/02/16.
 */
@Module(includes = HelperModule.class)
public class ApplicationModule {

    @Provides
    @Singleton
    public TwitterService provideTwitterService(RestClient restClient) {
        return restClient.getService();
    }
}
