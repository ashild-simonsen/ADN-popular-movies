package adn.als.popularmovies;

import android.app.FragmentTransaction;
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

        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.MovieDetailsFragment, movieDetailsFragment);
        ft.commit();
    }
}
