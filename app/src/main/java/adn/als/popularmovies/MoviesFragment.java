package adn.als.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MoviesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DownloadMoviesDataTask downloadMoviesDataTask = new DownloadMoviesDataTask(this.getActivity().getApplicationContext());
        downloadMoviesDataTask.execute("");


        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        return view;
    }
}
