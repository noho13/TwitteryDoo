package com.normanhoeller.twitterydoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MainView {


    private static final String TAG = MainActivity.class.getSimpleName();
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText query = (EditText) findViewById(R.id.et_query);
        FloatingActionButton goSearch = (FloatingActionButton) findViewById(R.id.btn_go);

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainPresenter.checkIfCredentialsAreReady()) {
                    String queryText = query.getText().toString();
                    if (!TextUtils.isEmpty(queryText)) {
                        mainPresenter.navigateToPictureActivity(queryText);
                    }
                } else {
                    mainPresenter.getAccessTokenFromTwitter();
                }

            }
        });

        mainPresenter = new MainPresenterImpl(this);

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

    @Override
    public void navigateToPictureActivity(String searchQuery) {
        Intent startActivity = new Intent(this, PictureActivity.class);
        startActivity.putExtra(PictureActivity.SEARCH_QUERY, searchQuery);
        startActivity(startActivity);
    }

    @Override
    public void showLoadingAnimation() {

    }

    @Override
    public void hideLoadingAnimation() {

    }
}
