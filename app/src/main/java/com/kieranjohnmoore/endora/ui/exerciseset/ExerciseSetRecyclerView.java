package com.kieranjohnmoore.endora.ui.exerciseset;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kieranjohnmoore.endora.databinding.ExerciseSetItemBinding;
import com.kieranjohnmoore.endora.model.ExerciseSet;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseSetRecyclerView extends RecyclerView.Adapter<ExerciseSetRecyclerView.ExerciseSetViewHolder> {
    private final String TAG = ExerciseSetRecyclerView.class.getSimpleName();

    private List<ExerciseSet> exerciseSets = Collections.emptyList();

    @NonNull
    @Override
    public ExerciseSetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ExerciseSetItemBinding itemBinding =
                ExerciseSetItemBinding.inflate(layoutInflater, viewGroup, false);
        return new ExerciseSetViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseSetViewHolder viewHolder, int i) {
        viewHolder.bind(exerciseSets.get(i), i);
    }

    @Override
    public int getItemCount() {
        return exerciseSets.size();
    }

    void updateExercises(List<ExerciseSet> exercises) {
        Log.d(TAG, "Updating sets for exercisxe");
        this.exerciseSets = exercises;
        this.notifyDataSetChanged();
    }

    class ExerciseSetViewHolder extends RecyclerView.ViewHolder {
        ExerciseSetItemBinding binding;
        ExerciseSet set;
        int id;

        ExerciseSetViewHolder(ExerciseSetItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(@NonNull final ExerciseSet set, int id) {
            this.set = set;
            this.id = id;
            binding.setSetNumber(id);
            binding.executePendingBindings();
        }
    }
}