package adn.als.popularmovies;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class MovieDetailsFragment extends Fragment {

    private static final String MOVIE_DETAILS_ARG_PARAM_MOVIE = "ARG_PARAM_MOVIE";

    private Movie movie;
    private View view;

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_DETAILS_ARG_PARAM_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(MOVIE_DETAILS_ARG_PARAM_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        initMovieTitle();
        initMoviePoster();
        initFavoriteToggleButton();
        initOriginalTitle();
        initReleaseDate();
        initVoteAverage();
        initPlotSynopsis();
        initTrailers();
        return this.view;
    }

    private void initMovieTitle(){
        TextView movieTitle = (TextView)this.view.findViewById(R.id.MovieTitle);
        movieTitle.setText(movie.getTitle());
    }

    private void initMoviePoster() {
        ImageView moviePoster = (ImageView)this.view.findViewById(R.id.MoviePoster);
        String posterImageURL = getActivity().getApplicationContext().getResources().getString(R.string.movie_base_url) + movie.getPosterPath();

        try {
            if(!posterImageURL.isEmpty()){

                Picasso.with(getActivity().getApplicationContext())
                        .load(posterImageURL)
                        .into(moviePoster);
            }
        }catch (Exception ex){
            Log.e(this.getClass().toString(), "Problem loading the poster image for movie." + ex.getMessage());
        }
    }

    private void initFavoriteToggleButton(){

        //TEMP:
        movie.setFavorite(true);

        ImageView favoriteIcon = (ImageView)this.view.findViewById(R.id.FavoriteIcon);
        favoriteIcon.setImageResource(movie.isFavorite() ? R.drawable.ic_favorite_yellow_24dp : R.drawable.ic_favorite_border_white_24dp);

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setFavorite(!movie.isFavorite());
                ((ImageView) v).setImageResource(movie.isFavorite() ? R.drawable.ic_favorite_yellow_24dp : R.drawable.ic_favorite_border_white_24dp);
            }
        });
    }

    private void initOriginalTitle(){
        TextView movieOriginalTitleText = (TextView)this.view.findViewById(R.id.MovieOrigionalTitleText);
        movieOriginalTitleText.setText(getString(R.string.movie_details_original_title_text));

        TextView movieOriginalTitle = (TextView)this.view.findViewById(R.id.MovieOrigionalTitle);
        movieOriginalTitle.setText(movie.getOriginalTitle());
    }

    private void initReleaseDate(){
        TextView releaseDateText = (TextView)this.view.findViewById(R.id.MovieReleaseDateText);
        releaseDateText.setText(getString(R.string.movie_details_released_text));

        TextView releaseDate = (TextView)this.view.findViewById(R.id.MovieReleaseDate);
        releaseDate.setText(!movie.getReleaseDate().equals("null") ? movie.getReleaseDate(): getResources().getString(R.string.movie_release_date_unknown));
    }

    private void initVoteAverage(){
        TextView voteAverageText = (TextView)this.view.findViewById(R.id.MovieVoteAverageText);
        voteAverageText.setText(getString(R.string.movie_details_average_vote_text));

        TextView voteAverage = (TextView)this.view.findViewById(R.id.MovieVoteAverage);
        voteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }

    private void initPlotSynopsis(){
        TextView plotSynopsis = (TextView)this.view.findViewById(R.id.MoviePlotSynopis);
        plotSynopsis.setText(!movie.getOverview().equals("null") ? movie.getOverview():"");
    }

    private void initTrailers(){
        ListView trailerList = (ListView)this.view.findViewById(R.id.TrailerList);
    }
}
