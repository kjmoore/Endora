package com.kieranjohnmoore.endora.database.dao;

import com.kieranjohnmoore.endora.model.TrainingPlanDay;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TrainingPlanDayDao {
    @Query("SELECT * FROM training_plan_day WHERE training_plan_id = :id")
    LiveData<List<TrainingPlanDay>> getTrainingPlanDaysForTrainingPlan(int id);
    @Insert
    void addDayPlan(TrainingPlanDay trainingPlanDay);
    @Delete
    void deleteDayPlan(TrainingPlanDay trainingPlan);
    @Query("SELECT * FROM training_plan_day WHERE id = :id")
    LiveData<TrainingPlanDay> getTrainingPlanDay(int id);
}
