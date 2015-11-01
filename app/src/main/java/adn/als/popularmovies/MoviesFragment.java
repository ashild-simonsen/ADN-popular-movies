package adn.als.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private GridView moviesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DownloadMoviesDataTask downloadMoviesDataTask = new DownloadMoviesDataTask(this.getActivity().getApplicationContext(), this);
        downloadMoviesDataTask.execute("");

        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        moviesListView = (GridView)view.findViewById(R.id.MoviesGrid);

        return view;
    }

    public void UpdateAdapter(ArrayList<Movie> movieList){

        if(movieAdapter == null){
            movieAdapter = new MovieAdapter(getActivity(),R.layout.list_item_movie, movieList);
            moviesListView.setAdapter(movieAdapter);
        }else{
            movieAdapter.addAll(movieList);
            movieAdapter.notifyDataSetChanged();
        }
    }
}
