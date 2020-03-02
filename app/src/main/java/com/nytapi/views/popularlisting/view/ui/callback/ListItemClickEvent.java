package com.nytapi.views.popularlisting.view.ui.callback;

import android.content.Intent;
import android.view.View;

import com.nytapi.views.populardetails.view.activity.PopularDetailsActivity;
import com.nytapi.views.popularlisting.model.ResultsBean;


public class ListItemClickEvent {

    /**
     * The purpose of this method to perform click event
     * @param v
     * @param resultsBean
     */
    public void onClickItem(View v, ResultsBean resultsBean) {
        //ToastUtil.showShortToast(v.getContext(), "Yes");
        Intent intent = new Intent(v.getContext(), PopularDetailsActivity.class);
        intent.putExtra("data",resultsBean);
        v.getContext().startActivity(intent);
    }
}
