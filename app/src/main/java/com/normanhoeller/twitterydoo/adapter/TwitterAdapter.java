package com.normanhoeller.twitterydoo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.normanhoeller.twitterydoo.R;
import com.normanhoeller.twitterydoo.model.ViewModelResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by norman on 02/02/16.
 */
public class TwitterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TwitterAdapter.class.getSimpleName();
    private static final int TYPE_VIEW = 0;
    private static final int TYPE_PROGRESS = 1;
    private List<ViewModelResult> pictureDataList;

    public TwitterAdapter(List<ViewModelResult> dataList) {
        pictureDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return pictureDataList.get(position) != null ? TYPE_VIEW : TYPE_PROGRESS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
                return new ItemViewHolder(view);
            case TYPE_PROGRESS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
                return new ProgressViewHolder(view);
            default:
                return null;
        }
    }

    public void addItems(List<ViewModelResult> items) {
        pictureDataList.addAll(items);
        this.notifyDataSetChanged();
    }

    public void addNullItemToShowProgressView() {
        pictureDataList.add(null);
        notifyItemInserted(pictureDataList.size() - 1);
    }

    public void removeNullItemToHideProgressView() {
        pictureDataList.remove(pictureDataList.size() -1);
        notifyItemRemoved(pictureDataList.size() - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_VIEW:
                populateItem((ItemViewHolder) viewHolder, position);
                break;
            case TYPE_PROGRESS:
                ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    private void populateItem(ItemViewHolder itemViewHolder, int position) {
        ViewModelResult item = pictureDataList.get(position);
        String url = item.getUrl();
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(itemViewHolder.imageView.getContext()).load(url).into(itemViewHolder.imageView);
        }

        if (!TextUtils.isEmpty(item.getText())) {
            itemViewHolder.description.setText(item.getText());
        }

        if (!TextUtils.isEmpty(item.getDate())) {
            itemViewHolder.date.setText(item.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return pictureDataList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView description;
        TextView date;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv_picture);
            this.description = (TextView) itemView.findViewById(R.id.tv_line1);
            this.date = (TextView) itemView.findViewById(R.id.tv_line2);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        ProgressViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }
}
