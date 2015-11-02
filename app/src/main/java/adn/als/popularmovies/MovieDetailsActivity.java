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
        this.setTitle(movie.getTitle());

        initMoviePoster();
        initMovieDetails();
    }

    private void initMovieDetails() {

        TextView movieTitle = (TextView)findViewById(R.id.MovieTitle);
        movieTitle.setText(movie.getTitle());

        TextView movieOriginalTitle = (TextView)findViewById(R.id.MovieOrigionalTitle);
        movieOriginalTitle.setText("Original title: " + movie.getOriginalTitle());

        TextView releaseDate = (TextView)findViewById(R.id.MovieReleaseDate);
        releaseDate.setText("Released: " + movie.getReleaseDate());

        TextView voteAverage = (TextView)findViewById(R.id.MovieVoteAverage);
        voteAverage.setText("Vote average: " + String.valueOf(movie.getVoteAverage()));

        TextView plotSynopsis = (TextView)findViewById(R.id.MoviePlotSynopis);
        plotSynopsis.setText(movie.getOverview());
    }

    private void initMoviePoster() {
        ImageView moviePoster = (ImageView)findViewById(R.id.MoviePoster);
        String posterImageURL = getApplicationContext().getResources().getString(R.string.movie_base_url) + movie.getPosterPath();

        try {
            if(!posterImageURL.isEmpty()){

                Picasso.with(getApplicationContext())
                        .load(posterImageURL)
                        .resize(100, 100)
                        .into(moviePoster);

            }
        }catch (Exception ex){
            Log.e(this.getClass().toString(), "Problem loading the poster image for movie." + ex.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
