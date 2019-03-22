package com.rohitsharma.moviesnew;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.rohitsharma.moviesnew.activities.movies.ModelMoviesActivity;
import com.rohitsharma.moviesnew.activities.movies.ViewModelMoviesActivity;
import com.rohitsharma.moviesnew.activities.movies.pojos.response.Datum;
import com.rohitsharma.moviesnew.activities.movies.pojos.response.MoviesResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class MoviesViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ModelMoviesActivity mockModel;

    private ViewModelMoviesActivity viewModel;

    @Captor
    private ArgumentCaptor<Consumer<MoviesResponse>> aCaptor;

    @Before
    public void setupViewModel() {
        MockitoAnnotations.initMocks(this);
        viewModel = new ViewModelMoviesActivity(mockModel);
    }

    @Test
    public void getMoviesTest() {
        MoviesResponse testMoviesResponse = new MoviesResponse();
        testMoviesResponse.setData(new ArrayList<>());

        viewModel.getMovies();
        Mockito.verify(mockModel).getMoviesModel(aCaptor.capture());
        Consumer<MoviesResponse> value = aCaptor.getValue();
        try {
            value.accept(testMoviesResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(viewModel.getMutableLiveData().getValue(), is(testMoviesResponse.getData()));

    }

    @Test
    public void getMoviesNullTest() {
        viewModel.getMovies();
        Mockito.verify(mockModel).getMoviesModel(aCaptor.capture());
        Consumer<MoviesResponse> value = aCaptor.getValue();
        try {
            value.accept(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mockito.verify(mockModel).someErrorIsThere();
    }

    @Test
    public void getCacheDataAvailableTest() {
        Mockito.when(mockModel.getCachedData(Datum.class)).thenReturn(null);
        viewModel.getCachedData();
        Mockito.verify(mockModel).getCachedData(Datum.class);
        Mockito.verify(mockModel).someErrorIsThere();
    }

    @Test
    public void checkDataAvailableTest() {
        Mockito.when(mockModel.checkDataAvailableInCache(Datum.class, false)).thenReturn(false);
        viewModel.checkIfDataAvInCache(false);
        Mockito.verify(mockModel).checkDataAvailableInCache(Datum.class, false);
        Mockito.verify(mockModel).getMoviesModel(Mockito.any(Consumer.class));
        Mockito.when(mockModel.checkDataAvailableInCache(Datum.class, true)).thenReturn(true);
        viewModel.checkIfDataAvInCache(true);
        Mockito.verify(mockModel).checkDataAvailableInCache(Datum.class, false);
        Mockito.verify(mockModel).getCachedData(Datum.class);


    }


}
