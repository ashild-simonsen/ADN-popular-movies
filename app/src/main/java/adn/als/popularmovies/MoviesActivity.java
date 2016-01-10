package adn.als.popularmovies;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MoviesActivity extends AppCompatActivity {

    private boolean showFavoritesOnly;
    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        moviesFragment = MoviesFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.MoviesFragment, moviesFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        updateShowMoviesByInMenu(menu);
        return true;
    }

    private void updateShowMoviesByInMenu(Menu menu) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrderPref = sharedPref.getString(getString(R.string.location_key_preferences_show_movies_by),
                getString(R.string.preferences_option_show_movies_by_popular));

        if(sortOrderPref.equals(getString(R.string.preferences_option_show_movies_by_highest_rated))){
            menu.findItem(R.id.menu_highest_rated).setChecked(true);
        }else if(sortOrderPref.equals(getString(R.string.preferences_option_show_movies_by_popular))){
            menu.findItem(R.id.menu_most_popular).setChecked(true);
        }else if(sortOrderPref.equals(getString(R.string.preferences_option_show_movies_by_favorites))){
            menu.findItem(R.id.menu_my_favorites).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        item.setChecked(true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        if(id == R.id.menu_highest_rated){
            editor.putString(getString(R.string.location_key_preferences_show_movies_by), getString(R.string.preferences_option_show_movies_by_highest_rated));
        }else if(id == R.id.menu_most_popular){
            editor.putString(getString(R.string.location_key_preferences_show_movies_by), getString(R.string.preferences_option_show_movies_by_popular));

        }else if(id == R.id.menu_my_favorites){
            editor.putString(getString(R.string.location_key_preferences_show_movies_by), getString(R.string.preferences_option_show_movies_by_favorites));
        }

        editor.commit();
        moviesFragment.downloadMovies();

        return super.onOptionsItemSelected(item);
    }
}
