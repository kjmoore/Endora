package com.kieranjohnmoore.endora.database.dao;

import com.kieranjohnmoore.endora.model.ExerciseSet;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ExerciseSetDao {
    @Query("SELECT * FROM exercise_set WHERE exercises_id = :id")
    LiveData<List<ExerciseSet>> getSetForExercise(int id);
    @Insert
    void addExercises(ExerciseSet trainingPlan);
    @Delete
    void deleteExercies(ExerciseSet exercises);
    @Query("SELECT * FROM exercise_set WHERE id = :id")
    LiveData<ExerciseSet> getExerciseSet(int id);
}
