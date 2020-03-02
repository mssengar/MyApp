package com.nytapi.views.popularlisting.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.nytapi.R;
import com.nytapi.databinding.ActivityMainBinding;
import com.nytapi.utils.ToastUtil;
import com.nytapi.views.popularlisting.model.PopularBean;
import com.nytapi.views.popularlisting.model.ResultsBean;
import com.nytapi.views.popularlisting.view.adapter.PopularRecyclerAdapter;
import com.nytapi.views.popularlisting.view.ui.callback.IPopularListener;
import com.nytapi.views.popularlisting.viewmodel.MostPopularViewModel;

import java.util.ArrayList;
import java.util.List;

public class PopularListActivity extends AppCompatActivity implements IPopularListener {

    private ActivityMainBinding binding;
    private MostPopularViewModel nyPopularApiModel;
    private List<ResultsBean> mResultDataList = new ArrayList<>();
    private PopularRecyclerAdapter mPupularRvAdapter;


    private void bindLiveDataObserver() {
        nyPopularApiModel.getArticle(true).observe(this, notificationObserver);
    }

    final Observer<PopularBean> notificationObserver = notificationModel -> {
        if (notificationModel == null) return;
        bindAdapter(notificationModel);
    };

    private void bindAdapter(PopularBean notificationModel) {
        mResultDataList.addAll(notificationModel.getResults());
        if (mPupularRvAdapter == null) {
            mPupularRvAdapter = new PopularRecyclerAdapter(this, mResultDataList);
            binding.rvNyArticles.setAdapter(mPupularRvAdapter);
        } else {
//            mCurrentDayAdapter.updateAdapter(this,appointmentDataList);
            mPupularRvAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        nyPopularApiModel = ViewModelProviders.of(this).get(MostPopularViewModel.class);
        binding.setViewModelVar(nyPopularApiModel);
        nyPopularApiModel.setArticleListener(this);
        bindLiveDataObserver();
    }

    private void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.rvNyArticles.setLayoutManager(mLayoutManager);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.ny_times_most_popular);
        }
    }

    @Override
    public void onSuccess(PopularBean article) {

    }

    @Override
    public void onFailure(String errorMessage) {
        ToastUtil.showShortToast(this, errorMessage);
    }

    @Override
    public void showProgressDialog(boolean isShowing) {
        if (isShowing) {
            binding.loadingProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loadingProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoDataFound() {

    }
}
