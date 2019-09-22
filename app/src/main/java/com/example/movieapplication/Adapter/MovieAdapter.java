package com.example.movieapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieapplication.Model.Movie;
import com.example.movieapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private int resource;
    ArrayList<Movie> objects;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieViewHolder movieViewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, null);
            movieViewHolder = new MovieViewHolder();
            movieViewHolder.movieImage = convertView.findViewById(R.id.movie_image);
            movieViewHolder.nameOfTheMovie = convertView.findViewById(R.id.name_of_the_movie);
            convertView.setTag(movieViewHolder);
        } else {
            movieViewHolder = (MovieViewHolder) convertView.getTag();

        }
        Movie movieObject = objects.get(position);
        if (!movieObject.getPosterPath().equals("")) {
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movieObject.getPosterPath()).placeholder(R.drawable.download).error(R.drawable.download).into(movieViewHolder.movieImage);
        }
        if (movieObject.getOriginalTitle() != null) {
            movieViewHolder.nameOfTheMovie.setText(movieObject.getOriginalTitle());
        }

        return convertView;
    }

}

class MovieViewHolder {
    ImageView movieImage;
    TextView nameOfTheMovie;

}
