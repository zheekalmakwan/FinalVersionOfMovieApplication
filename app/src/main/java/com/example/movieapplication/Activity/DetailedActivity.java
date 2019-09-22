package com.example.movieapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.movieapplication.Adapter.FavouriteMovieAdapter;
import com.example.movieapplication.DataBase.AppDataBase;
import com.example.movieapplication.DataBase.MovieEntry;
import com.example.movieapplication.Executor.AppExecutors;
import com.example.movieapplication.Loader.TrailerAsyncTaskLoader;
import com.example.movieapplication.Model.Trailer;
import com.example.movieapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {

    public static String base_url_string = "https://api.themoviedb.org/3/movie/";
    String movieID;
    public static String apiKey = "6bd35dffd85f01de3d9e9c9612f622f7";
    public static String remainingPath = "/videos?api_key=";
    public static String url_string;
    String title;
    String release_date;
    String overview;
    Float rating;
    String poster_path;
    String key;
    TextView overView;
    TextView releaseData;
    TextView nameOfTheMovie;
    ImageView movieImage;
    RatingBar ratingbar;
    ImageView playMovie;
    LoaderManager loaderManager;
    private AppDataBase mDb;
    String trigger;
    MovieEntry movieEntry;
    FavouriteMovieAdapter favouriteMovieAdapter;
    int position;
    List<MovieEntry> savedmovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        overView = findViewById(R.id.overview_of_the_movie);
        releaseData = findViewById(R.id.release_date);
        nameOfTheMovie = findViewById(R.id.movie_name);
        movieImage = findViewById(R.id.image_of__the_movie);
        ratingbar = findViewById(R.id.rating);
        playMovie = findViewById(R.id.play_image);

        Intent intent = getIntent();
        if (intent.hasExtra("trigger")) {
            trigger = intent.getStringExtra("trigger");
        }

        if (intent.hasExtra("position")) {
            position = intent.getIntExtra("position", 0);
        }
        if (intent.hasExtra("movieID")) {
            movieID = intent.getStringExtra("movieID");
        }

        if (intent.hasExtra("nameOfTheMovie")) {
            title = intent.getStringExtra("nameOfTheMovie");
            nameOfTheMovie.setText(title);
        }
        if (intent.hasExtra("release_date")) {
            release_date = intent.getStringExtra("release_date");
            releaseData.setText(release_date);
        }
        if (intent.hasExtra("overview")) {
            overview = intent.getStringExtra("overview");
            overView.setText(overview);
        }
        if (intent.hasExtra("rating")) {
            rating = Float.valueOf(intent.getStringExtra("rating"));
            ratingbar.setRating(rating / 2);
        }
        if (intent.hasExtra("poster_path")) {
            poster_path = intent.getStringExtra("poster_path");
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + poster_path)
                    .placeholder(R.drawable.download).error(R.drawable.download).into(movieImage);
        }

        url_string = base_url_string + movieID + remainingPath + apiKey;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        loaderManager = getSupportLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(0, null, this).forceLoad();
        }

        playMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                startActivity(showTrailer);
            }
        });

        mDb = AppDataBase.getAppInstance(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu_setting, menu);
        MenuItem item = menu.findItem(R.id.rate);
        if (trigger.equals("1")) {
            item.setIcon(R.drawable.ic_star_white_24dp);
        } else if (trigger.equals("2")) {
            item.setIcon(R.drawable.ic_star_yellow_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate:
                if (item.getIcon().getConstantState().equals(
                        getResources().getDrawable(R.drawable.ic_star_white_24dp).getConstantState())) {
                    movieEntry = new MovieEntry(Integer.valueOf(movieID), rating, poster_path, title, overview, release_date);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.movieDao().InsertMovie(movieEntry);
                        }
                    });
                    item.setIcon(R.drawable.ic_star_yellow_24dp);
                } else if (item.getIcon().getConstantState().equals(
                        getResources().getDrawable(R.drawable.ic_star_yellow_24dp).getConstantState())) {
                    MovieEntry movies = mDb.movieDao().loadMovieByID(Integer.valueOf(movieID));
                    mDb.movieDao().deleteMovie(movies);
                    finish();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @NonNull
    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri uriBase = Uri.parse(url_string);
        Uri.Builder builder = uriBase.buildUpon();
        builder.build();
        return new TrailerAsyncTaskLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {
        if (data.size() != 0) {
            key = data.get(0).getKey();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Trailer>> loader) {

    }
}