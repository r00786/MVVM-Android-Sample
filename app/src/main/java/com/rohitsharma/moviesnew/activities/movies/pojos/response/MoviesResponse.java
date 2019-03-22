package com.rohitsharma.moviesnew.activities.movies.pojos.response;

import com.google.gson.annotations.Expose;

import java.util.List;


public class MoviesResponse {

    @Expose
    private List<Datum> data;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
