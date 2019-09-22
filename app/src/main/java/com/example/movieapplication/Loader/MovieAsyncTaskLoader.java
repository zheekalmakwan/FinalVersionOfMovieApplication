package com.example.movieapplication.Loader;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.example.movieapplication.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    Context context;
    private String url_string;

    public MovieAsyncTaskLoader(Context context, String url_string) {
        super(context);
        this.context = context;
        this.url_string = url_string;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try {

            URL newsUrl = new URL(url_string);
            HttpURLConnection newsHttpURLConnection = (HttpURLConnection) newsUrl.openConnection();
            newsHttpURLConnection.setRequestMethod("GET");
            newsHttpURLConnection.setConnectTimeout(10000);
            newsHttpURLConnection.setReadTimeout(15000);
            newsHttpURLConnection.connect();
            InputStream newsInputStream = newsHttpURLConnection.getInputStream();
            InputStreamReader newsInputStreamReader = new InputStreamReader(newsInputStream);
            BufferedReader newsBufferReader = new BufferedReader(newsInputStreamReader);
            String lineOfData = newsBufferReader.readLine();
            StringBuilder JSONData = new StringBuilder();
            while (lineOfData != null) {
                JSONData.append(lineOfData);
                lineOfData = newsBufferReader.readLine();
            }
            String movieID = "";
            String movieImage = "";
            String nameOfTheMovie = "";
            Double voting = null;
            String date = "";
            String overview = "";
            JSONObject root = new JSONObject(JSONData.toString());
            if (root.has("results")) {
                JSONArray movieJSONArray = root.getJSONArray("results");
                for (int i = 0; i < movieJSONArray.length(); i++) {
                    JSONObject elements = movieJSONArray.getJSONObject(i);
                    if (elements.has("id")) {
                        movieID = String.valueOf(elements.getInt("id"));
                    }
                    if (elements.has("original_title")) {
                        nameOfTheMovie = elements.getString("original_title");
                    }
                    if (elements.has("poster_path")) {
                        movieImage = elements.getString("poster_path");
                    }
                    if (elements.has("vote_average")) {
                        voting = Double.valueOf(elements.getString("vote_average"));
                    }
                    if (elements.has("release_date")) {
                        date = elements.getString("release_date");
                    }
                    if (elements.has("overview")) {
                        overview = elements.getString("overview");
                    }

                    movieArrayList.add(new Movie(movieID, nameOfTheMovie, movieImage, voting, date, overview));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return movieArrayList;
    }
}
