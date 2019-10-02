package com.kieranjohnmoore.endora.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = TrainingPlanDay.class,
        parentColumns = "id",
        childColumns = "day_plan_id",
        onDelete = CASCADE),
        tableName = "exercise")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "day_plan_id")
    public int dayPlanId;
    @ColumnInfo(name = "rest_between_sets")
    public int restBetweenSets;
    public String name;
}
