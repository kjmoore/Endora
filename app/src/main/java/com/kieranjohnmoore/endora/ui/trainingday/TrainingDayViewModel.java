package com.kieranjohnmoore.endora.ui.trainingday;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.model.Exercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class TrainingDayViewModel extends AndroidViewModel {
    private static String TAG = TrainingDayViewModel.class.getSimpleName();

    private LiveData<List<Exercise>> exercises;

    TrainingDayViewModel(@NonNull Application application, int id) {
        super(application);

        Log.d(TAG, "Created new Day Plan View Model");

        exercises = AppDatabase.getInstance(this.getApplication()).exerciseDao().getExercisesForDay(id);
    }

    LiveData<List<Exercise>> getExercises() {
        return exercises;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int id;

        Factory(@NonNull Application application, int id) {
            mApplication = application;
            this.id = id;
        }

        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TrainingDayViewModel(mApplication, id);
        }
    }
}
