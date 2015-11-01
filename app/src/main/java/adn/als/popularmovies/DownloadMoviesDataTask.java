package adn.als.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

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

public class DownloadMoviesDataTask extends AsyncTask {

    private BufferedReader reader = null;
    private Context context = null;
    private MoviesFragment moviesFragment;
    private ArrayList<Movie> movieList;

    public DownloadMoviesDataTask(Context context, MoviesFragment moviesFragment){
        this.context = context;
        this.moviesFragment = moviesFragment;
    }

    @Override
    protected void onPostExecute(Object o) {
        moviesFragment.UpdateAdapter(movieList);
    }

    @Override
    protected String doInBackground(Object[] param) {

        String jsonResult = fetchMovieList();
        movieList = parseJsonToMovieList(jsonResult);
        return jsonResult;
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
        boolean sortingByRating = true;

        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String PARAM_SORT_BY = "sort_by";
        final String PARAM_API_KEY = "api_key";
        final String SORT_BY_POPULARITY = "popularity.desc";

        final String SORT_BY_RATING = "vote_average.desc";
        final String PARAM_PAGE_NO = "page";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_PAGE_NO, String.valueOf(pageNo))
                .appendQueryParameter(PARAM_SORT_BY, sortingByRating ? SORT_BY_RATING : SORT_BY_POPULARITY)
                .appendQueryParameter(PARAM_API_KEY, context.getResources().getString(R.string.themoviedb_api_key))
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
            movie.setTitle(jsonMovie.getString("title"));
            movie.setVideo(jsonMovie.getBoolean("video"));
            movie.setVoteAverage(jsonMovie.getDouble("vote_average"));
            movie.setVoteCount(jsonMovie.getInt("vote_count"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
