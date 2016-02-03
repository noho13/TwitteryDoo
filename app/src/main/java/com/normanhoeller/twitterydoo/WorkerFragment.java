package com.normanhoeller.twitterydoo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.normanhoeller.twitterydoo.api.TwitterService;
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
 * Created by norman on 02/02/16.
 */
public class WorkerFragment extends Fragment {

    public static final String FRAG_TAG = "frag_tag";
    private static final String TAG = WorkerFragment.class.getSimpleName();
    @Inject
    public TwitterService twitterService;
    private Callback callback;
    private String authString;
    private SearchResult.SearchMetaData nextResults;
    private Map<String, String> queryMap;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = context instanceof Activity ? (Callback) context : null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        String accessToken = getActivity().getSharedPreferences(MainActivity.ACCESS_TOKEN_PREFS, Context.MODE_PRIVATE).getString(MainActivity.ACCESS_TOKEN, null);
        authString = "Bearer " + accessToken;
        queryMap = new HashMap<>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TwitteryDooApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    public void queryTwitterService(String query) {
        if (TextUtils.isEmpty(query) && nextResults == null) {
            return;
        } else if (TextUtils.isEmpty(query) && nextResults != null && nextResults.getNext_results() != null) {
            String nextMaxId = nextResults.getNext_results().split("max_id=")[1].split("&")[0];
            query = nextResults.getQuery();
            queryMap.put("max_id", nextMaxId);
            queryMap.put("include_entities", String.valueOf(true));
            callback.showProgressView();
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

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface Callback {
        void setResult(List<ViewModelResult> searchResult);
        void showProgressView();
    }
}
