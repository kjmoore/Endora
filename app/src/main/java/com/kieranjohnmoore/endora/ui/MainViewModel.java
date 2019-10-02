package com.kieranjohnmoore.endora.ui;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.database.ExerciseDownloader;
import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {
    private static String TAG = MainViewModel.class.getSimpleName();

    private MutableLiveData<List<String>> exercises = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Downloading exercises");
        final ExerciseDownloader downloader = new ExerciseDownloader(this);
        downloader.execute();
    }

    public LiveData<List<String>> getExercises() {
        return exercises;
    }

    public void setDownloadedExercises(List<String> exercises) {
        this.exercises.setValue(exercises);
    }
}
