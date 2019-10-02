package com.kieranjohnmoore.endora.ui.dayplan;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.model.DayPlan;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DayPlanViewModel extends AndroidViewModel {
    private static String TAG = DayPlanViewModel.class.getSimpleName();

    private LiveData<List<DayPlan>> dayPlans;
    private final int id;

    public DayPlanViewModel(@NonNull Application application, int id) {
        super(application);

        Log.d(TAG, "Created new Day Plan View Model");
        this.id = id;

        dayPlans = AppDatabase.getInstance(this.getApplication()).dayPlanDao().getDayPlansForTrainingPlan(id);
    }

    LiveData<List<DayPlan>> getDayPlans() {
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
            return (T) new DayPlanViewModel(mApplication, id);
        }
    }
}
