package adn.als.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadMoviesDataTask extends AsyncTask<Void, Integer, Void> {

    private BufferedReader reader = null;
    private Context context = null;
    private MoviesFragment moviesFragment;
    private ArrayList<Movie> movieList;
    private ProgressBar progressBar;
    private int progressStatusDownload;

    public DownloadMoviesDataTask(Context context, MoviesFragment moviesFragment, ProgressBar progressBar){
        this.context = context;
        this.moviesFragment = moviesFragment;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressStatusDownload = 0;
        progressBar.setProgress(progressStatusDownload);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        String jsonResult = fetchMovieList();

        progressStatusDownload = 50;
        publishProgress(progressStatusDownload);

        movieList = parseJsonToMovieList(jsonResult);

        progressStatusDownload = 100;
        publishProgress(progressStatusDownload);

        return null;
    }

    @Nullable
    private String fetchMovieList() {
        URL url = null;
        HttpURLConnection connection = null;
        String movieJsonString = null;

        try {
            url = createUrlToFetchMovies();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream in = new BufferedInputStream(connection.getInputStream());
            if(in != null) {
                movieJsonString = readStream(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(getClass().toString(), "Error in URL", e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().toString(), "Error reading stream", e);
        }finally {
            if(connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(getClass().toString(), "Error closing stream", e);
                }
            }
        }
        return movieJsonString;
    }

    private URL createUrlToFetchMovies() throws MalformedURLException {

        //temp:
        int pageNo = 1;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrderPref = sharedPref.getString(context.getString(R.string.preferences_sorting_location_key),
                                                    context.getString(R.string.preferences_sorting_default_value_popular));
        boolean sortingByRating = sortOrderPref.equals(context.getString(R.string.preferences_sorting_default_value_popular)) ? false:true;

        Resources res = context.getResources();

        Uri uri = Uri.parse(res.getString(R.string.movielist_base_url)).buildUpon()
                .appendQueryParameter(res.getString(R.string.movielist_param_page), String.valueOf(pageNo))
                .appendQueryParameter(res.getString(R.string.movielist_param_sort_by),
                                        sortingByRating ? res.getString(R.string.movielist_param_value_sort_by_rating)
                                                        : res.getString(R.string.movielist_param_value_sort_by_popularity))
                .appendQueryParameter(res.getString(R.string.movielist_param_api_key), res.getString(R.string.themoviedb_api_key))
                .build();

        return new URL(uri.toString());
    }

    private String readStream(InputStream in) throws IOException {

        reader = new BufferedReader(new InputStreamReader(in));

        StringBuffer buffer = new StringBuffer();
        String line;

        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        return buffer.length() == 0 ? null:buffer.toString();
    }

    private ArrayList<Movie> parseJsonToMovieList(String result) {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            JSONObject jsonResults  = new JSONObject(result);
            JSONArray jsonMovieList = jsonResults.getJSONArray("results");

            for(int i = 0; i < jsonMovieList.length(); i++){
                movies.add(parseMovie(jsonMovieList.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private Movie parseMovie(JSONObject jsonMovie){
        Movie movie = new Movie();

        try {
            movie.setAdult(jsonMovie.getBoolean("adult"));
            movie.setBackdropPath(jsonMovie.getString("backdrop_path"));
            //TODO: jsonMovie.getJSONArray("genre_ids") into movie.setGenreIds;
            movie.setId(jsonMovie.getInt("id"));
            movie.setOriginalLanguage(jsonMovie.getString("original_language"));
            movie.setOriginalTitle(jsonMovie.getString("original_title"));
            movie.setOverview(jsonMovie.getString("overview"));
            movie.setPosterPath(jsonMovie.getString("poster_path"));
            movie.setPopularity(jsonMovie.getDouble("popularity"));
            movie.setReleaseDate(jsonMovie.getString("release_date"));
            movie.setTitle(jsonMovie.getString("title"));
            movie.setVideo(jsonMovie.getBoolean("video"));
            movie.setVoteAverage(jsonMovie.getDouble("vote_average"));
            movie.setVoteCount(jsonMovie.getInt("vote_count"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.progressBar != null && values != null) {
            progressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        moviesFragment.UpdateAdapter(movieList);
        progressBar.setVisibility(View.GONE);
    }
}
