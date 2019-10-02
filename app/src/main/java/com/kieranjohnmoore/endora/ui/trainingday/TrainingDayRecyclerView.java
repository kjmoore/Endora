package com.kieranjohnmoore.endora.ui.trainingday;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.endora.databinding.TrainingDayItemBinding;
import com.kieranjohnmoore.endora.model.Exercise;
import com.kieranjohnmoore.endora.ui.MainActivity;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrainingDayRecyclerView extends RecyclerView.Adapter<TrainingDayRecyclerView.TrainingPlanViewHolder> {
    private final String TAG = TrainingDayRecyclerView.class.getSimpleName();
    private final int dayId;

    private List<Exercise> exercises = Collections.emptyList();

    TrainingDayRecyclerView(int dayId) {
        this.dayId = dayId;
    }

    @NonNull
    @Override
    public TrainingPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final TrainingDayItemBinding itemBinding =
                TrainingDayItemBinding.inflate(layoutInflater, viewGroup, false);
        return new TrainingPlanViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingPlanViewHolder viewHolder, int i) {
        viewHolder.bind(exercises.get(i));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    void updateExercises(List<Exercise> exercises) {
        Log.d(TAG, "Updating training exercises");
        this.exercises = exercises;
        this.notifyDataSetChanged();
    }

    class TrainingPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TrainingDayItemBinding binding;
        Exercise exercise;

        TrainingPlanViewHolder(TrainingDayItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final Exercise exercise) {
            this.exercise = exercise;
            binding.setExerciseName(exercise.name);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + exercise.name);
            final Intent intent = new Intent(MainActivity.EXERCISE_SET_FRAG);
            intent.putExtra(MainActivity.ID_PARAM, exercise.id);
            intent.putExtra(MainActivity.DAY_ID_PARAM, dayId);
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}