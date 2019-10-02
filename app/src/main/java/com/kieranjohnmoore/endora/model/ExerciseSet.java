package com.kieranjohnmoore.endora.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = ExerciseSet.class,
        parentColumns = "id",
        childColumns = "exercises_id",
        onDelete = CASCADE),
        tableName = "exercise_set")
public class ExerciseSet {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "exercises_id")
    public int exercisesId;
    @ColumnInfo(name = "number_of_reps")
    public int numberOfReps;
    public int weight;
}
