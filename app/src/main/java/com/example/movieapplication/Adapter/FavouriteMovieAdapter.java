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

import com.example.movieapplication.DataBase.MovieEntry;
import com.example.movieapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteMovieAdapter extends ArrayAdapter<MovieEntry> {

    private Context context;
    private int resource;
    List<MovieEntry> objects;

    public FavouriteMovieAdapter(@NonNull Context context, int resource, @NonNull List<MovieEntry> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FavouriteMovieViewHolder favouriteMovieViewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, null);
            favouriteMovieViewHolder = new FavouriteMovieViewHolder();
            favouriteMovieViewHolder.movieImage = convertView.findViewById(R.id.movie_image);
            favouriteMovieViewHolder.nameOfTheMovie = convertView.findViewById(R.id.name_of_the_movie);
            convertView.setTag(favouriteMovieViewHolder);
        } else {
            favouriteMovieViewHolder = (FavouriteMovieViewHolder) convertView.getTag();

        }
        MovieEntry movieObject = objects.get(position);
        if (!movieObject.getPosterPath().equals("")) {
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movieObject.getPosterPath())
                    .placeholder(R.drawable.download).error(R.drawable.download)
                    .into(favouriteMovieViewHolder.movieImage);
        }
        if (movieObject.getOriginalTitle() != null) {
            favouriteMovieViewHolder.nameOfTheMovie.setText(movieObject.getOriginalTitle());
        }

        return convertView;
    }

    public List<MovieEntry> getObjects() {
        return objects;
    }
}

class FavouriteMovieViewHolder {
    ImageView movieImage;
    TextView nameOfTheMovie;

}

