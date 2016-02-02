package com.normanhoeller.twitterydoo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.normanhoeller.twitterydoo.R;
import com.normanhoeller.twitterydoo.model.ViewModelResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by norman on 31/08/15.
 */
public class TwitterAdapter extends RecyclerView.Adapter<TwitterAdapter.ViewHolder> {

    private static final String TAG = TwitterAdapter.class.getSimpleName();
    private List<ViewModelResult> pictureDataList;

    public TwitterAdapter(List<ViewModelResult> dataList) {
        pictureDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new ViewHolder(view);
    }

    public void addItems(List<ViewModelResult> items) {
        pictureDataList.addAll(items);
        this.notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ViewModelResult item = pictureDataList.get(position);
        String url = item.getUrl();
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(viewHolder.imageView.getContext()).load(url).into(viewHolder.imageView);
        }

        if (!TextUtils.isEmpty(item.getText())) {
            viewHolder.description.setText(item.getText());
        }

        if (!TextUtils.isEmpty(item.getDate())) {
            viewHolder.date.setText(item.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return pictureDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView description;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv_picture);
            this.description = (TextView) itemView.findViewById(R.id.tv_line1);
            this.date = (TextView) itemView.findViewById(R.id.tv_line2);
        }
    }
}
