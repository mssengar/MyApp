package com.nytapi.views.popularlisting.services;


import com.nytapi.views.popularlisting.model.PopularBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MostPopularApiServices {
    @GET("1.json")
    Observable<PopularBean> getNewsList(@Query("api-key") String apiKey);
}
