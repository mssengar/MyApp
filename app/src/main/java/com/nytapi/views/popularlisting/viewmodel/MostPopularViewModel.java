package com.nytapi.views.popularlisting.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nytapi.R;
import com.nytapi.network.ApiClient;
import com.nytapi.network.CheckConnection;
import com.nytapi.views.popularlisting.model.PopularBean;
import com.nytapi.views.popularlisting.services.MostPopularApiServices;
import com.nytapi.views.popularlisting.view.ui.callback.IPopularListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MostPopularViewModel extends AndroidViewModel {
    private IPopularListener mArticleListener;
    private MutableLiveData<PopularBean> articleMutableLiveData;
    private Disposable disposable;

    public MostPopularViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * The purpose of this method to implement listner
     *
     * @param mArticleListener
     */
    public void setArticleListener(IPopularListener mArticleListener) {
        this.mArticleListener = mArticleListener;
    }

    //we will call this method to get the data
    public LiveData<PopularBean> getArticle(boolean isShowing) {
        if (articleMutableLiveData == null) {
            articleMutableLiveData = new MutableLiveData<PopularBean>();
            loadArticles(isShowing);
        }
        return articleMutableLiveData;
    }

    //This method is using Retrofit to get the JSON data from URL
    public void loadArticles(boolean isShowing) {
        if (CheckConnection.isConnected(getApplication().getApplicationContext())) {
            callArticleApi(isShowing);
        } else {
            mArticleListener.onFailure(getApplication().getApplicationContext().getString(R.string.check_internet_connection));
            mArticleListener.showProgressDialog(false);
        }
    }

    //This method is using Request the api using retrofit
    private void callArticleApi(boolean isShowing) {
        Retrofit retrofit = ApiClient.getApiClient();

        if (retrofit == null) return;
        if (isShowing) {
            mArticleListener.showProgressDialog(true);
        }
        Observable<PopularBean> articleObserver = retrofit.create(MostPopularApiServices.class).getNewsList("aTve5PkTXY09Impz3enmjMP0A4dQz2iT");
        disposable = articleObserver.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::handleResponse, this::handleError);
        //subscribe.dispose();
    }

    /**
     * The purpose of this method to handle response
     *
     * @param articleBean
     */
    private void handleResponse(PopularBean articleBean) {
        if (articleBean != null && articleBean.getStatus().equals("OK")) {
            mArticleListener.showProgressDialog(false);
            mArticleListener.onSuccess(articleBean);
            articleMutableLiveData.setValue(articleBean);
        } else {
            mArticleListener.showProgressDialog(false);
            mArticleListener.showNoDataFound();
        }
    }

    /**
     * The purpose of  this method to handle error
     *
     * @param throwable
     */
    private void handleError(Throwable throwable) {
        mArticleListener.showProgressDialog(false);
        mArticleListener.onFailure(throwable.getMessage());
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
