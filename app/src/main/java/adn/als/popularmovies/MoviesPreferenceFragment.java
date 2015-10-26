package adn.als.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class MoviesPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_movies);
        updateSortedBySummary();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSortedBySummary();
    }

    private void updateSortedBySummary() {
        Preference pref = findPreference(getString(R.string.preferences_sorting_location_key));

        if(pref != null) {
            ListPreference listPreferece = (ListPreference) pref;
            listPreferece.setSummary(getString(R.string.preferences_sorting_sorted_by) + listPreferece.getEntry().toString().toLowerCase());
        }
    }
}
