package com.kieranjohnmoore.endora.ui.exerciseset;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.model.Exercise;
import com.kieranjohnmoore.endora.model.ExerciseSet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class ExerciseSetViewModel extends AndroidViewModel {
    private static String TAG = ExerciseSetViewModel.class.getSimpleName();

    private LiveData<List<ExerciseSet>> sets;
    private LiveData<Exercise> exercise;

    private ExerciseSetViewModel(@NonNull Application application, int id) {
        super(application);

        Log.d(TAG, "Created new Exercise Set View Model");

        sets = AppDatabase.getInstance(this.getApplication()).exerciseSetDao().getSetForExercise(id);
        exercise = AppDatabase.getInstance(this.getApplication()).exerciseDao().getExercie(id);
    }

    LiveData<List<ExerciseSet>> getSets() {
        return sets;
    }
    LiveData<Exercise> getExercise() {
        return exercise;
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
            return (T) new ExerciseSetViewModel(mApplication, id);
        }
    }
}
