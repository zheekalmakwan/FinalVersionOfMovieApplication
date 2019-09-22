package com.example.movieapplication.Activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.movieapplication.Adapter.FavouriteMovieAdapter;
import com.example.movieapplication.Adapter.MovieAdapter;
import com.example.movieapplication.DataBase.AppDataBase;
import com.example.movieapplication.DataBase.MovieEntry;
import com.example.movieapplication.Loader.MovieAsyncTaskLoader;
import com.example.movieapplication.MainViewModel;
import com.example.movieapplication.Model.Movie;
import com.example.movieapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    TextView noInternet;
    GridView movieGridList;
    public static String url_string = "https://api.themoviedb.org/3/movie/popular?api_key=6bd35dffd85f01de3d9e9c9612f622f7";
    LoaderManager loaderManager;
    MovieAdapter movieAdapter;
    FavouriteMovieAdapter favouriteMovieAdapter;
    int trigger = 1;
    private AppDataBase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noInternet = (TextView) findViewById(R.id.no_network_tv);
        movieGridList = (GridView) findViewById(R.id.movie_data_lv);
        noInternet.setVisibility(View.INVISIBLE);
        movieGridList.setVisibility(View.INVISIBLE);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        loaderManager = getSupportLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(0, null, this).forceLoad();
        } else {
            movieGridList.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            noInternet.setText("Internet Connection Required");

        }

        mDb = AppDataBase.getAppInstance(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                trigger = 1;
                url_string = "https://api.themoviedb.org/3/movie/top_rated?api_key=6bd35dffd85f01de3d9e9c9612f622f7";
                loaderManager.restartLoader(0, null, this).forceLoad();
                return true;
            case R.id.popular:
                trigger = 1;
                url_string = "https://api.themoviedb.org/3/movie/popular?api_key=6bd35dffd85f01de3d9e9c9612f622f7";
                loaderManager.restartLoader(0, null, this).forceLoad();
                return true;
            case R.id.favorite:
                trigger = 2;
                retrieveMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void retrieveMovies() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(final List<MovieEntry> movieEntries) {
                favouriteMovieAdapter = new FavouriteMovieAdapter
                        (getApplicationContext(), R.layout.grid_view_list_style, movieEntries);
                movieGridList.setAdapter(favouriteMovieAdapter);
                movieGridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                        intent.putExtra("trigger", String.valueOf(trigger));
                        intent.putExtra("position", i);
                        intent.putExtra("movieID", String.valueOf(movieEntries.get(i).getMovieID()));
                        intent.putExtra("nameOfTheMovie", movieEntries.get(i).getOriginalTitle());
                        intent.putExtra("release_date", movieEntries.get(i).getReleaseDate());
                        intent.putExtra("overview", movieEntries.get(i).getOverview());
                        intent.putExtra("rating", movieEntries.get(i).getVoteAverage().toString());
                        intent.putExtra("poster_path", movieEntries.get(i).getPosterPath());

                        startActivity(intent);

                    }
                });
            }
        });


    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        Uri uriBase = Uri.parse(url_string);
        Uri.Builder builder = uriBase.buildUpon();
        builder.build();
        return new MovieAsyncTaskLoader(this, builder.toString());
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, final ArrayList<Movie> data) {

        movieGridList.setVisibility(View.VISIBLE);
        if (trigger == 1) {
            movieAdapter = new MovieAdapter(this, R.layout.grid_view_list_style, data);
            movieGridList.setAdapter(movieAdapter);

            movieGridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                    intent.putExtra("trigger", String.valueOf(trigger));
                    intent.putExtra("movieID", String.valueOf(data.get(i).getId()));
                    intent.putExtra("nameOfTheMovie", data.get(i).getOriginalTitle());
                    intent.putExtra("release_date", data.get(i).getReleaseDate());
                    intent.putExtra("overview", data.get(i).getOverview());
                    intent.putExtra("rating", data.get(i).getVoteAverage().toString());
                    intent.putExtra("poster_path", data.get(i).getPosterPath());
                    startActivity(intent);

                }
            });
        }
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (loaderManager == null) {
            loaderManager.initLoader(0, null, this).forceLoad();

        } else {
            loaderManager.restartLoader(0, null, this).forceLoad();
        }
    }
}
