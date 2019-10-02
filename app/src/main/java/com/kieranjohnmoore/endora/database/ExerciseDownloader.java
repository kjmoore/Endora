package com.kieranjohnmoore.endora.database;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kieranjohnmoore.endora.ui.MainViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ExerciseDownloader extends AsyncTask<Integer, Void, List<String>> {
    private static final String TAG = ExerciseDownloader.class.getSimpleName();
    private static final String EXERCISE_URL = "https://json.kieranjohnmoore.com";

    private WeakReference<MainViewModel> adapterReference;

    public ExerciseDownloader(MainViewModel app) {
        this.adapterReference = new WeakReference<>(app);
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            final InputStream in = urlConnection.getInputStream();

            final Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            final boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return "";
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    protected List<String> doInBackground(Integer... pageNumber) {
        final Uri uri = Uri.parse(EXERCISE_URL);

        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        Log.d(TAG, "Connecting to URL: " + url);

        try {
            final String data = getResponseFromHttpUrl(url);

            if (!data.isEmpty()) {
                final Type listType = new TypeToken<List<String>>() {}.getType();
                return new Gson().fromJson(data, listType);
            }
        } catch (IOException e) {
            Log.e(TAG, "Could not download data", e);
        }

        return Collections.emptyList();
    }

    @Override
    protected void onPostExecute(List<String> exercises) {
        final MainViewModel app = adapterReference.get();
        if (app != null) {
            app.setDownloadedExercises(exercises);
        }

        super.onPostExecute(exercises);
    }
}