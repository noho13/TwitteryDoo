package com.normanhoeller.twitterydoo.api;


import com.normanhoeller.twitterydoo.model.AuthenticationJSON;
import com.normanhoeller.twitterydoo.model.SearchResult;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by norman on 02/02/16.
 */
public interface TwitterService {

    @FormUrlEncoded
    @POST("/oauth2/token")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
    Observable<AuthenticationJSON> postTokens(@Header("Authorization") String authorization, @Field("grant_type") String text);

    @GET("/1.1/search/tweets.json?count=10")
    Observable<SearchResult> getSearchResult(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);
}
