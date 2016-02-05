package com.normanhoeller.twitterydoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.normanhoeller.twitterydoo.DataManager;
import com.normanhoeller.twitterydoo.PictureActivity;
import com.normanhoeller.twitterydoo.R;
import com.normanhoeller.twitterydoo.adapter.TwitterAdapter;
import com.normanhoeller.twitterydoo.model.ViewModelResult;

import java.util.List;

/**
 * Created by norman on 02/02/16.
 */
public class PictureFragment extends Fragment implements DataManager.Callback {

    private static final String TAG = PictureFragment.class.getSimpleName();
    private static final String PROGRESS_BAR_VISIBILITY = "progress_bar_visibility";
    private static final int PAGE_SIZE = 10;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView toolbarTitle;
    private boolean isLoading;
    private DataManager dataManager;

    public static PictureFragment createInstance(String searchQuery) {
        PictureFragment fragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PictureActivity.SEARCH_QUERY, searchQuery);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_picture, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_item_grid);
        progressBar = (ProgressBar) root.findViewById(R.id.pr_loading);
//        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.holo_orange_light), android.graphics.PorterDuff.Mode.MULTIPLY);
        toolbarTitle = (TextView) root.findViewById(R.id.tv_toolbar_title);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataManager = DataManager.getInstance();
        dataManager.setCallback(this);
        if (savedInstanceState != null) {
            int progressVisibility = savedInstanceState.getInt(PROGRESS_BAR_VISIBILITY);
            if (progressVisibility == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                Log.d(TAG, "visibleItemCount: " + visibleItemCount);
                Log.d(TAG, "totalItemCount: " + totalItemCount);
                Log.d(TAG, "firstVisibleItemCount: " + firstVisibleItemPosition);

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        isLoading = true;
                        queryTwitter(null);
                    }
                }
            }
        });

        String searchQuery = getArguments().getString(PictureActivity.SEARCH_QUERY);
        toolbarTitle.setText("looking for: " + searchQuery.toLowerCase());
        queryTwitter(searchQuery);

    }


    private void queryTwitter(String query) {
        dataManager.queryTwitterService(query);
    }

    private void showSnackBar(View view) {
        Snackbar.make(view, R.string.no_results, Snackbar.LENGTH_LONG)
                .setAction(R.string.hit_back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                })
                .show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PROGRESS_BAR_VISIBILITY, progressBar.getVisibility());
    }

    @Override
    public void setResult(List<ViewModelResult> searchResult) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        // adding items to adapter
        if (recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() != 0) {
            ((TwitterAdapter) recyclerView.getAdapter()).removeNullItemToHideProgressView();
            if (searchResult != null) {
                ((TwitterAdapter) recyclerView.getAdapter()).addItems(searchResult);
            }
            isLoading = false;
            return;
        }

        // setting items for the first time
        recyclerView.setAdapter(new TwitterAdapter(searchResult));
        if (searchResult.size() == 0) {
            showSnackBar(recyclerView);
        }
    }

    @Override
    public void showProgressView() {
        ((TwitterAdapter) recyclerView.getAdapter()).addNullItemToShowProgressView();
    }

    @Override
    public void onDetach() {
        dataManager.setCallback(null);
        super.onDetach();
    }
}
