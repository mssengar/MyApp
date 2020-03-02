package com.nytapi.views.popularlisting.view.ui.callback;


import com.nytapi.views.popularlisting.model.PopularBean;

public interface IPopularListener {
     void onSuccess(PopularBean article);
     void onFailure(String errorMessage);
     void showProgressDialog(boolean isShowing);
     void showNoDataFound();
}
