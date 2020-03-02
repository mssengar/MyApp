package com.nytapi.views.popularlisting.view.adapter;

import android.content.Context;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nytapi.R;
import com.nytapi.databinding.RvListItemBinding;
import com.nytapi.views.popularlisting.model.ResultsBean;
import com.nytapi.views.popularlisting.view.ui.callback.ListItemClickEvent;

import java.util.List;

public class PopularRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ResultsBean> mResultList;
    private List<ResultsBean> mResultTempList;
    private Context mContext;

    public PopularRecyclerAdapter(Context mContext, List<ResultsBean> list) {
        this.mContext = mContext;
        this.mResultList = list;
        this.mResultTempList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_list_item, parent, false);
        return new PopularViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            ResultsBean model = mResultList.get(position);
            ((PopularViewHolder) holder).binding.setResultPojo(model);
            ((PopularViewHolder) holder).binding.executePendingBindings();
            ((PopularViewHolder) holder).binding.setHandlers(new ListItemClickEvent());
            Glide.with(((PopularViewHolder) holder).binding.profileImage.getContext())
                    .load(model.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((PopularViewHolder) holder).binding.profileImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder {
         RvListItemBinding binding;

        PopularViewHolder(RvListItemBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }
}
