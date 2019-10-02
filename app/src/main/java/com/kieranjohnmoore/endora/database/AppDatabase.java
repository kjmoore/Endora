package com.kieranjohnmoore.endora.database;

import android.content.Context;
import android.util.Log;

import com.kieranjohnmoore.endora.database.dao.ExerciseSetDao;
import com.kieranjohnmoore.endora.database.dao.ExercisesDao;
import com.kieranjohnmoore.endora.database.dao.TrainingPlanDao;
import com.kieranjohnmoore.endora.database.dao.TrainingPlanDayDao;
import com.kieranjohnmoore.endora.model.Exercise;
import com.kieranjohnmoore.endora.model.ExerciseSet;
import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TrainingPlan.class, com.kieranjohnmoore.endora.model.TrainingPlanDay.class, Exercise.class, ExerciseSet.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "endura_training_plans";
    private static AppDatabase database;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                Log.d(TAG, "Creating new database instance");
                database = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return database;
    }

    public static ExecutorService getExecutor() {
        synchronized (LOCK) {
            return executor;
        }
    }

    public abstract TrainingPlanDao trainingPlanDao();
    public abstract ExerciseSetDao exerciseSetDao();
    public abstract ExercisesDao exerciseDao();
    public abstract TrainingPlanDayDao dayPlanDao();
}
