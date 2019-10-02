package com.kieranjohnmoore.endora.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = TrainingPlan.class,
        parentColumns = "id",
        childColumns = "training_plan_id",
        onDelete = CASCADE),
        tableName = "training_plan_day")
public class TrainingPlanDay {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "training_plan_id")
    public int trainingPlanId;
    public String summery;
}
