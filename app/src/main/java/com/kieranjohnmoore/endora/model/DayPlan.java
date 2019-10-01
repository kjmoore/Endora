package com.kieranjohnmoore.endora.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = TrainingPlan.class,
        parentColumns = "id",
        childColumns = "training_plan_id"), tableName = "day_plan")
public class DayPlan {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int day;
    @ColumnInfo(name = "training_plan_id")
    public int trainingPlanId;
}
