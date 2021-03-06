package adn.als.popularmovies;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private GridView moviesListView;
    private String currentSorting = "";
    private ProgressBar progressBar;

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MoviesFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        moviesListView = (GridView)view.findViewById(R.id.MoviesGrid);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarLoadingMovies);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadMovies();
    }

    public void downloadMovies(){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String sortOrderPref = sharedPref.getString(getString(R.string.location_key_preferences_show_movies_by),
                                                    getString(R.string.preferences_option_show_movies_by_popular));

        if(!currentSorting.equals(sortOrderPref)){
            currentSorting = sortOrderPref;
            DownloadMoviesDataTask downloadMoviesDataTask = new DownloadMoviesDataTask(this.getActivity().getApplicationContext(), this, progressBar);
            downloadMoviesDataTask.execute();
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
