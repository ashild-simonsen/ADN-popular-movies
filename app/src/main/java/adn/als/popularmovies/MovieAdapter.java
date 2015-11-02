package adn.als.popularmovies;

import android.content.Context;
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
                    .resize(100, 100)
                    .into(moviePoster);

            }
        }catch (Exception ex){
            Log.e(this.getClass().toString(), "Problem loading the poster image for movie." + ex.getMessage());
        }

        return convertView;
    }
}
