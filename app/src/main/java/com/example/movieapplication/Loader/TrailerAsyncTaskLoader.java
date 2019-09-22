package com.example.movieapplication.Loader;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.example.movieapplication.Model.Trailer;

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

public class TrailerAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Trailer>> {

    Context context;
    private String url_string;

    public TrailerAsyncTaskLoader(Context context, String url_string) {
        super(context);
        this.context = context;
        this.url_string = url_string;
    }

    @Override
    public ArrayList<Trailer> loadInBackground() {

        ArrayList<Trailer> trailerArrayList = new ArrayList<>();
        try {

            URL newsUrl = new URL(url_string);
            HttpURLConnection newsHttpURLConnection = (HttpURLConnection) newsUrl.openConnection();
            newsHttpURLConnection.setRequestMethod("GET");
            newsHttpURLConnection.setConnectTimeout(20000);
            newsHttpURLConnection.setReadTimeout(25000);
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

            String key = "";

            JSONObject root = new JSONObject(JSONData.toString());
            if (root.has("results")) {
                JSONArray movieJSONArray = root.getJSONArray("results");
                for (int i = 0; i < movieJSONArray.length(); i++) {
                    JSONObject elements = movieJSONArray.getJSONObject(i);
                    if (elements.has("key")) {
                        key = elements.getString("key");

                    }

                    trailerArrayList.add(new Trailer(key));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return trailerArrayList;
    }
}

