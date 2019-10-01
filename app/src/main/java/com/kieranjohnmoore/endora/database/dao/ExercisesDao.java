package com.kieranjohnmoore.endora.database.dao;

import com.kieranjohnmoore.endora.model.DayPlan;
import com.kieranjohnmoore.endora.model.Exercises;
import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ExercisesDao {
    @Query("SELECT * FROM exercises WHERE day_plan_id = :id")
    LiveData<List<Exercises>> getExerciesForDay(int id);
    @Insert
    void addExercises(Exercises trainingPlan);
    @Delete
    void deleteExercies(Exercises exercises);
    @Query("SELECT * FROM exercises WHERE id = :id")
    LiveData<Exercises> getExercies(int id);
}
