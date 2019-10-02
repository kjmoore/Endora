package com.kieranjohnmoore.endora.database.dao;

import com.kieranjohnmoore.endora.model.Exercise;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ExercisesDao {
    @Query("SELECT * FROM Exercise WHERE day_plan_id = :id")
    LiveData<List<Exercise>> getExercisesForDay(int id);
    @Insert
    void addExercise(Exercise trainingPlan);
    @Delete
    void deleteExercie(Exercise exercise);
    @Query("SELECT * FROM Exercise WHERE id = :id")
    LiveData<Exercise> getExercie(int id);
    @Update
    void updateExercise(Exercise exercise);
}
