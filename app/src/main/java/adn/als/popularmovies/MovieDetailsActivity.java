package adn.als.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        this.movie = (Movie) getIntent().getExtras().get(getString(R.string.movie_details_intent_extra_movie));

        initMovieTitle();
        initMoviePoster();
        initOriginalTitle();
        initReleaseDate();
        initVoteAverage();
        initPlotSynopsis();
    }

    private void initMovieTitle(){
        this.setTitle(movie.getTitle());

        TextView movieTitle = (TextView)findViewById(R.id.MovieTitle);
        movieTitle.setText(movie.getTitle());
    }

    private void initMoviePoster() {
        ImageView moviePoster = (ImageView)findViewById(R.id.MoviePoster);
        String posterImageURL = getApplicationContext().getResources().getString(R.string.movie_base_url) + movie.getPosterPath();

        try {
            if(!posterImageURL.isEmpty()){

                Picasso.with(getApplicationContext())
                        .load(posterImageURL)
                        .into(moviePoster);
            }
        }catch (Exception ex){
            Log.e(this.getClass().toString(), "Problem loading the poster image for movie." + ex.getMessage());
        }
    }

    private void initOriginalTitle(){
        TextView movieOriginalTitleText = (TextView)findViewById(R.id.MovieOrigionalTitleText);
        movieOriginalTitleText.setText(getString(R.string.movie_details_original_title_text));

        TextView movieOriginalTitle = (TextView)findViewById(R.id.MovieOrigionalTitle);
        movieOriginalTitle.setText(movie.getOriginalTitle());
    }

    private void initReleaseDate(){
        TextView releaseDateText = (TextView)findViewById(R.id.MovieReleaseDateText);
        releaseDateText.setText(getString(R.string.movie_details_released_text));

        TextView releaseDate = (TextView)findViewById(R.id.MovieReleaseDate);
        releaseDate.setText(movie.getReleaseDate());
    }

    private void initVoteAverage(){
        TextView voteAverageText = (TextView)findViewById(R.id.MovieVoteAverageText);
        voteAverageText.setText(getString(R.string.movie_details_average_vote_text));

        TextView voteAverage = (TextView)findViewById(R.id.MovieVoteAverage);
        voteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }

    private void initPlotSynopsis(){
        TextView plotSynopsis = (TextView)findViewById(R.id.MoviePlotSynopis);
        plotSynopsis.setText(movie.getOverview());
    }
}
