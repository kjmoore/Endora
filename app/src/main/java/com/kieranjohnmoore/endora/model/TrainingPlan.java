package com.kieranjohnmoore.endora.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "training_plans")
public class TrainingPlan {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
}
