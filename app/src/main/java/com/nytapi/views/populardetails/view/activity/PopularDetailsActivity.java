package com.nytapi.views.populardetails.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nytapi.R;
import com.nytapi.databinding.ActivityDetailsBinding;
import com.nytapi.views.populardetails.viewmodel.DetailsViewModel;
import com.nytapi.views.popularlisting.model.ResultsBean;

public class PopularDetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding detailsBinding;
    private ResultsBean mResultsBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        DetailsViewModel mDetailsActivityViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        detailsBinding.setViewModel(mDetailsActivityViewModel);
        setMyActionBar();
        mResultsBean = getIntent().getParcelableExtra("data");

        if (mResultsBean != null) {
            detailsBinding.setResultPojo(mResultsBean);

            Glide.with(detailsBinding.profileImage.getContext())
                    .load(mResultsBean.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(detailsBinding.profileImage);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mResultsBean.getTitle());
            }
        }
    }

    /**
     * set Toolbar with backPress feature with
     * corresponding  fragment or activity
     */
    private void setMyActionBar() {
        setSupportActionBar(detailsBinding.singleToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
