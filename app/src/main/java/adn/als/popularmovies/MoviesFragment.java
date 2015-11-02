package adn.als.popularmovies;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private GridView moviesListView;
    private String currentSorting = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        moviesListView = (GridView)view.findViewById(R.id.MoviesGrid);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadMovies();
    }

    public void downloadMovies(){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String sortOrderPref = sharedPref.getString(getString(R.string.preferences_sorting_location_key),
                                                    getString(R.string.preferences_sorting_default_value_popular));

        if(!currentSorting.equals(sortOrderPref)){
            currentSorting = sortOrderPref;
            DownloadMoviesDataTask downloadMoviesDataTask = new DownloadMoviesDataTask(this.getActivity().getApplicationContext(), this);
            downloadMoviesDataTask.execute("");
        }
    }

    public void UpdateAdapter(ArrayList<Movie> movieList){

        if(movieAdapter == null){
            movieAdapter = new MovieAdapter(getActivity(),R.layout.list_item_movie, movieList);
            moviesListView.setAdapter(movieAdapter);
        }else{
            movieAdapter.clear();
            movieAdapter.addAll(movieList);
            movieAdapter.notifyDataSetChanged();
        }
    }
}
