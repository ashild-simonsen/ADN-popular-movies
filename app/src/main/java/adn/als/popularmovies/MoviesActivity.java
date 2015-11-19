package adn.als.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.view.Menu;
import android.view.MenuItem;

public class MoviesActivity extends AppCompatActivity {

    private boolean showFavoritesOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        initActionButtonFavorite(menu);
        return true;
    }

    private void initActionButtonFavorite(Menu menu) {

        //TEMP
        showFavoritesOnly = true;

        MenuItemImpl favoriteMenuItem = (MenuItemImpl) menu.findItem(R.id.action_favorite);
        favoriteMenuItem.setIcon(showFavoritesOnly ? R.drawable.ic_favorite_yellow_24dp : R.drawable.ic_favorite_border_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, PreferenceActivity.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_favorite) {
            showFavoritesOnly = !showFavoritesOnly;
            item.setIcon(showFavoritesOnly ? R.drawable.ic_favorite_yellow_24dp : R.drawable.ic_favorite_border_white_24dp);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
