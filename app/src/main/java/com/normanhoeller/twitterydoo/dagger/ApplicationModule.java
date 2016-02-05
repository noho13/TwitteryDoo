package com.normanhoeller.twitterydoo.dagger;

import android.content.Context;

import com.normanhoeller.twitterydoo.MainPresenter;
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

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public TwitterService provideTwitterService(RestClient restClient) {
        return restClient.getService();
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

}
