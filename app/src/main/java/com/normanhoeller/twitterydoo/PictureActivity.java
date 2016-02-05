package com.normanhoeller.twitterydoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.normanhoeller.twitterydoo.model.ViewModelResult;
import com.normanhoeller.twitterydoo.ui.PictureFragment;

import java.util.List;

/**
 * Created by norman on 31/08/15.
 */
public class PictureActivity extends AppCompatActivity{

    public static final String SEARCH_QUERY = "search_query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            PictureFragment fragment = PictureFragment.createInstance(getIntent().getStringExtra(SEARCH_QUERY));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
}
