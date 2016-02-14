package com.normanhoeller.twitterydoo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.normanhoeller.twitterydoo.api.TwitterService;
import com.normanhoeller.twitterydoo.model.AuthenticationJSON;
import com.normanhoeller.twitterydoo.model.SearchResult;
import com.normanhoeller.twitterydoo.model.ViewModelResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by norman on 04/02/16.
 * Singleton hosting TwitterService to access Data
 */
public class DataManager {

    public static final String ACCESS_TOKEN_PREFS = "access_token_prefs";
    public static final String ACCESS_TOKEN = "access_token";
    private static final String TAG = DataManager.class.getSimpleName();
    private static DataManager instance;
    @Inject
    public TwitterService twitterService;
    @Inject
    public Context context;
    private String authInfo = "ygvd3VVOy9u068cvychbcpSri:d8FNUIE5s3AReyFHcXPVlyJOm8u8srPzfXdNNg3gTfi1KZBiEX";
    private String auth = "Basic " + Base64.encodeToString(authInfo.getBytes(), Base64.NO_WRAP);
    private String body = "client_credentials";

    private Callback callback;
    String accessToken;
    private String authString;
    private SearchResult.SearchMetaData nextResults;
    private Map<String, String> queryMap = new HashMap<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void getAccessTokenFromTwitter() {
        twitterService.postTokens(auth, body)
                .subscribeOn(Schedulers.io()) // upwards runs on io
                .observeOn(AndroidSchedulers.mainThread()) // downwards runs on mainThread;
                .subscribe(new Action1<AuthenticationJSON>() {
                    @Override
                    public void call(AuthenticationJSON authenticationJSON) {
                        if (authenticationJSON.getToken_type().equalsIgnoreCase("bearer")) {
                            String accessToken = authenticationJSON.getAccess_token();
                            storeAccessTokenInSharedPrefs(accessToken);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "error while trying to get accesstoken from Twitter");
                    }
                });
    }

    public void storeAccessTokenInSharedPrefs(String accessToken) {
        authString = "Bearer " + accessToken;
        SharedPreferences prefs = context.getSharedPreferences(ACCESS_TOKEN_PREFS, Context.MODE_PRIVATE);
        prefs.edit().putString(ACCESS_TOKEN, accessToken).commit();
    }

    public boolean isAccessTokenAvailable() {
        accessToken = context.getSharedPreferences(ACCESS_TOKEN_PREFS, Context.MODE_PRIVATE).getString(ACCESS_TOKEN, null);
        Log.d(TAG, "accessToken is: " + accessToken);
        if (accessToken != null) {
            authString = "Bearer " + accessToken;
        }
        return accessToken != null;
    }

    public void queryTwitterService(String query) {
        if (TextUtils.isEmpty(query) && nextResults == null) {
            return;
        } else if (TextUtils.isEmpty(query) && nextResults != null && nextResults.getNext_results() != null) {
            String nextMaxId = nextResults.getNext_results().split("max_id=")[1].split("&")[0];
            query = nextResults.getQuery();
            queryMap.put("max_id", nextMaxId);
            queryMap.put("include_entities", String.valueOf(true));
            if (callback != null) {
                callback.showLoadMoreProgressView();
            }
        } else if (nextResults != null && nextResults.getNext_results() == null) {
            // no more tweets
            return;
        }

        queryMap.put("q", query);
        twitterService.getSearchResult(authString, queryMap)
                .map(new Func1<SearchResult, List<ViewModelResult>>() {
                    @Override
                    public List<ViewModelResult> call(SearchResult searchResult) {
                        List<ViewModelResult> results = new ArrayList<>();
                        nextResults = searchResult.getSearch_metadata();

                        for (SearchResult.Statuses result : searchResult.getStatuses()) {
                            results.add(new ViewModelResult(result.getUser().getProfile_image_url(), result.getText(), result.getUser().getCreated_at()));
                        }
                        return results;
                    }
                })
                .subscribeOn(Schedulers.io()) // upwards runs on io
                .observeOn(AndroidSchedulers.mainThread()) // downwards runs on mainThread
                .subscribe(new Action1<List<ViewModelResult>>() {
                    @Override
                    public void call(List<ViewModelResult> searchResult) {
                        Log.d(TAG, "got searchresult: " + searchResult);
                        if (callback != null) {
                            callback.setResult(searchResult);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "onError: " + throwable.getMessage());
                    }
                });
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void setResult(List<ViewModelResult> searchResult);

        void showLoadMoreProgressView();
    }
}
