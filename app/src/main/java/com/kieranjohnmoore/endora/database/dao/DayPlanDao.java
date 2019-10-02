package com.kieranjohnmoore.endora.database.dao;

import com.kieranjohnmoore.endora.model.DayPlan;
import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DayPlanDao {
    @Query("SELECT * FROM day_plan WHERE training_plan_id = :id")
    LiveData<List<DayPlan>> getDayPlansForTrainingPlan(int id);
    @Insert
    void addDayPlan(DayPlan trainingPlan);
    @Delete
    void deleteDayPlan(DayPlan trainingPlan);
    @Query("SELECT * FROM day_plan WHERE id = :id")
    LiveData<DayPlan> getDayPlan(int id);
}
