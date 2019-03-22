package com.rohitsharma.moviesnew.data;

import com.rohitsharma.moviesnew.activities.movies.pojos.response.MoviesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkCalls {
    @GET("movies/")
    Single<MoviesResponse> getMoviesrequest();


}
