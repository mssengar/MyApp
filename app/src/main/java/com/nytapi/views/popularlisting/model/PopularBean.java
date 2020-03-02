package com.nytapi.views.popularlisting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PopularBean implements Parcelable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<ResultsBean> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.copyright);
        dest.writeValue(this.numResults);
        dest.writeList(this.results);
    }

    public PopularBean() {
    }

    protected PopularBean(Parcel in) {
        this.status = in.readString();
        this.copyright = in.readString();
        this.numResults = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = new ArrayList<ResultsBean>();
        in.readList(this.results, ResultsBean.class.getClassLoader());
    }

    public static final Creator<PopularBean> CREATOR = new Creator<PopularBean>() {
        @Override
        public PopularBean createFromParcel(Parcel source) {
            return new PopularBean(source);
        }

        @Override
        public PopularBean[] newArray(int size) {
            return new PopularBean[size];
        }
    };
}
