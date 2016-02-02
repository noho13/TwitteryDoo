package com.normanhoeller.twitterydoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.normanhoeller.twitterydoo.api.TwitterService;
import com.normanhoeller.twitterydoo.model.AuthenticationJSON;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String ACCESS_TOKEN_PREFS = "access_token_prefs";
    public static final String ACCESS_TOKEN = "access_token";
    @Inject
    public TwitterService twitterService;
    public String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TwitteryDooApplication) getApplication()).getComponent().inject(this);

        final EditText query = (EditText) findViewById(R.id.et_query);
        FloatingActionButton goSearch = (FloatingActionButton) findViewById(R.id.btn_go);

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryText = query.getText().toString();
                if (!TextUtils.isEmpty(queryText) && !TextUtils.isEmpty(access_token)) {
                    startSearchActivity(queryText);
                }
            }
        });

        access_token = getSharedPreferences(ACCESS_TOKEN_PREFS, MODE_PRIVATE).getString(ACCESS_TOKEN, null);
        if (access_token == null) {

            String authInfo = "ygvd3VVOy9u068cvychbcpSri:d8FNUIE5s3AReyFHcXPVlyJOm8u8srPzfXdNNg3gTfi1KZBiEX";
            String auth = "Basic " + Base64.encodeToString(authInfo.getBytes(), Base64.NO_WRAP);
            String body = "client_credentials";

            twitterService.postTokens(auth, body)
                    .subscribeOn(Schedulers.io()) // upwards runs on io
                    .observeOn(AndroidSchedulers.mainThread()) // downwards runs on mainThread;
                    .subscribe(new Action1<AuthenticationJSON>() {
                        @Override
                        public void call(AuthenticationJSON authenticationJSON) {
                            if (authenticationJSON.getToken_type().equalsIgnoreCase("bearer")) {
                                access_token = authenticationJSON.getAccess_token();
                                storeAccessTokenInSharedPrefs();
                                Log.d(TAG, "access_token: " + access_token);
                            }
                        }
                    });
        }
    }

    private void storeAccessTokenInSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences(ACCESS_TOKEN_PREFS, MODE_PRIVATE);
        prefs.edit().putString(ACCESS_TOKEN, access_token).commit();
    }

    private void startSearchActivity(@NonNull String searchQuery) {
        Intent startActivity = new Intent(this, PictureActivity.class);
        startActivity.putExtra(PictureActivity.SEARCH_QUERY, searchQuery);
        startActivity(startActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
