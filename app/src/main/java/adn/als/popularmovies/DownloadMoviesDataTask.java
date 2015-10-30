package adn.als.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadMoviesDataTask extends AsyncTask {

    private BufferedReader reader = null;
    private Context mContext = null;

    public DownloadMoviesDataTask(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(Object[] param) {

        URL url = null;
        HttpURLConnection connection = null;
        String movieJsonString = null;

        try {
            url = createUrl();
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

        Log.d(getClass().toString(), "movieJsonString: " + movieJsonString);
        return movieJsonString;
    }

    private URL createUrl() throws MalformedURLException {

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
                .appendQueryParameter(PARAM_API_KEY, mContext.getResources().getString(R.string.themoviedb_api_key))
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
}
