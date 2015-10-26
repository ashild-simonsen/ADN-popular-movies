package adn.als.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MoviesPreferenceFragment())
                .commit();
    }
}
