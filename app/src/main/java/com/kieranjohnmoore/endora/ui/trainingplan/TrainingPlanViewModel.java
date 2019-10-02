package com.kieranjohnmoore.endora.ui.trainingplan;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.model.TrainingPlanDay;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrainingPlanViewModel extends AndroidViewModel {
    private static String TAG = TrainingPlanViewModel.class.getSimpleName();

    private LiveData<List<TrainingPlanDay>> dayPlans;

    public TrainingPlanViewModel(@NonNull Application application, int id) {
        super(application);

        Log.d(TAG, "Created new Day Plan View Model");

        dayPlans = AppDatabase.getInstance(this.getApplication()).dayPlanDao().getTrainingPlanDaysForTrainingPlan(id);
    }

    LiveData<List<TrainingPlanDay>> getDayPlans() {
        return dayPlans;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int id;

        public Factory(@NonNull Application application, int id) {
            mApplication = application;
            this.id = id;
        }

        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TrainingPlanViewModel(mApplication, id);
        }
    }
}
