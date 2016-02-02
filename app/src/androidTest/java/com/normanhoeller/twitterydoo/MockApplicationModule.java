package com.normanhoeller.twitterydoo;


import com.normanhoeller.twitterydoo.api.TwitterService;
import com.normanhoeller.twitterydoo.model.AuthenticationJSON;
import com.normanhoeller.twitterydoo.model.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by norman on 02/02/16.
 */
@Module
public class MockApplicationModule {

    @Provides
    @Singleton
    public TwitterService provideTwitterService() {
        return new TwitterService() {
            @Override
            public Observable<AuthenticationJSON> postTokens(@Header("Authorization") String authorization, @Field("grant_type") String text) {
                return null;
            }

            @Override
            public Observable<SearchResult> getSearchResult(@Header("Authorization") String authorization, @QueryMap Map<String, String> options) {
                SearchResult searchResult = new SearchResult();
                SearchResult.Statuses status = new SearchResult.Statuses();
                SearchResult.User user = new SearchResult.User();
                status.setText("This is a fake tweet");
                status.setUser(user);
                List<SearchResult.Statuses> statusesList = new ArrayList<>();
                statusesList.add(status);
                searchResult.setStatuses(statusesList);

                return Observable.just(searchResult);
            }

        };
    }
}
