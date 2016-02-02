package com.normanhoeller.twitterydoo.api;

import android.util.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by norman on 02/02/16.
 */
public class RestClient {

    private static final String BASE_URL = "https://api.twitter.com";
    private static RestClient restClient;
    private static TwitterService service;

    private RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
//                .setRequestInterceptor(new RequestInterceptor() {
//                    @Override
//                    public void intercept(RequestInterceptor.RequestFacade request) {
////                        String authInfo = "944caf99dfc4aa6186b0:d459648217e840dc9ffc77aea5a001cd4a972e4b";
////                        String auth = "Bearer " + Base64.encodeToString(authInfo.getBytes(), Base64.NO_WRAP);
//                        String auth = "Bearer " + bearer;
//                        request.addHeader("Authorization", auth);
//                    }
//                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        service = restAdapter.create(TwitterService.class);
    }

    public TwitterService getService() {
        return service;
    }

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

}
