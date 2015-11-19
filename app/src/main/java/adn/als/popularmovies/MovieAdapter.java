package adn.als.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_movie, null);
        }

        ImageView moviePoster = (ImageView) convertView.findViewById(R.id.MoviePoster);
        Movie movie = movieList.get(position);
        String posterImageURL = context.getResources().getString(R.string.movie_base_url) + movie.getPosterPath();

        try {
            if(!posterImageURL.isEmpty()){

                Picasso.with(getContext())
                    .load(posterImageURL)
                    .into(moviePoster);
            }
        }catch (Exception ex){
            Log.e(this.getClass().toString(), "Problem loading the poster image for movie." + ex.getMessage());
        }

        convertView.setId(movie.getId());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie movie = null;
                Integer movieId = v.getId();
                for (int i = 0; i < movieList.size(); i++) {
                    if (movieId.equals(movieList.get(i).getId())) {
                        movie = movieList.get(i);
                        break;
                    }
                }
                if (movie != null) {
                    Intent movieDetailsIntent = new Intent(context, MovieDetailsActivity.class);
                    movieDetailsIntent.putExtra(context.getString(R.string.movie_details_intent_extra_movie), movie);
                    v.getContext().startActivity(movieDetailsIntent);
                }
            }
        });

        ///TEMP:
        movie.setFavorite(true);

        ImageView favoriteIcon = (ImageView)convertView.findViewById(R.id.FavoriteIcon);
        favoriteIcon.setVisibility(movie.isFavorite() ? View.VISIBLE:View.INVISIBLE);

        return convertView;
    }
}
