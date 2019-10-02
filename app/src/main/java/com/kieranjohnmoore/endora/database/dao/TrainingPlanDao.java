package com.kieranjohnmoore.endora.database.dao;

import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TrainingPlanDao {
    @Query("SELECT * FROM training_plans")
    LiveData<List<TrainingPlan>> loadAllTrainingPlans();
    @Insert
    void addTrainingPlan(TrainingPlan trainingPlan);
    @Delete
    void deleteTrainingPlan(TrainingPlan trainingPlan);
    @Query("SELECT * FROM training_plans WHERE id = :id")
    LiveData<TrainingPlan> getTrainingPlan(int id);
    @Update
    void updateTrainingPlan(TrainingPlan plan);
}
