package com.normanhoeller.twitterydoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.normanhoeller.twitterydoo.model.SearchResult;
import com.normanhoeller.twitterydoo.model.ViewModelResult;
import com.normanhoeller.twitterydoo.ui.PictureFragment;

import java.util.List;

/**
 * Created by norman on 31/08/15.
 */
public class PictureActivity extends AppCompatActivity implements WorkerFragment.Callback {

    public static final String SEARCH_QUERY = "search_query";
    private WorkerFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWorker();
        if (savedInstanceState == null) {
            PictureFragment fragment = PictureFragment.createInstance(getIntent().getStringExtra(SEARCH_QUERY));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }

    private void setupWorker() {
        fragment = (WorkerFragment) getSupportFragmentManager().findFragmentByTag(WorkerFragment.FRAG_TAG);
        if (fragment == null) {
            fragment = new WorkerFragment();
            getSupportFragmentManager().beginTransaction().add(fragment, WorkerFragment.FRAG_TAG).commit();
        }
    }

    public void sendQuery(String query) {
        fragment.queryShutterStockService(query);
    }

    @Override
    public void setResult(List<ViewModelResult> searchResult) {
        PictureFragment fragment = (PictureFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        if (fragment != null) {
            fragment.setResult(searchResult);
        }
    }
}
