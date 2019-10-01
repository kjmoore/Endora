package com.kieranjohnmoore.endora.ui.trainingplanlist;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TrainingPlanViewModel extends AndroidViewModel {
    private static String TAG = TrainingPlanViewModel.class.getSimpleName();

    private LiveData<List<TrainingPlan>> trainingPlans;

    public TrainingPlanViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Created new Training Plan View Model");
        trainingPlans = AppDatabase.getInstance(this.getApplication()).trainingPlanDao().loadAllTrainingPlans();
    }

    LiveData<List<TrainingPlan>> getTrainingPlans() {
        return trainingPlans;
    }
}
