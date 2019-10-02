package com.kieranjohnmoore.endora.ui.trainingplan;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.endora.databinding.TrainingPlanItemBinding;
import com.kieranjohnmoore.endora.model.TrainingPlanDay;
import com.kieranjohnmoore.endora.ui.MainActivity;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrainingPlanRecyclerView extends RecyclerView.Adapter<TrainingPlanRecyclerView.TrainingPlanViewHolder> {
    private final String TAG = TrainingPlanRecyclerView.class.getSimpleName();

    private List<TrainingPlanDay> trainingPlanDays = Collections.emptyList();

    @NonNull
    @Override
    public TrainingPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final TrainingPlanItemBinding itemBinding =
                TrainingPlanItemBinding.inflate(layoutInflater, viewGroup, false);
        return new TrainingPlanViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingPlanViewHolder viewHolder, int i) {
        viewHolder.bind(trainingPlanDays.get(i), i);
    }

    @Override
    public int getItemCount() {
        return trainingPlanDays.size();
    }

    void updateDayPlans(List<TrainingPlanDay> trainingPlanDays) {
        Log.d(TAG, "Updating training plans");
        this.trainingPlanDays = trainingPlanDays;
        this.notifyDataSetChanged();
    }

    class TrainingPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TrainingPlanItemBinding binding;
        TrainingPlanDay trainingPlanDay;
        int dayNumber;

        TrainingPlanViewHolder(TrainingPlanItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final TrainingPlanDay trainingPlanDay, final int dayNumber) {
            this.trainingPlanDay = trainingPlanDay;
            this.dayNumber = dayNumber;
            binding.setDayNumber(dayNumber + 1);
            //TODO: Work out Exercise list
            binding.setExerciseSummary("TEST");
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + trainingPlanDay.id);

            final Intent intent = new Intent(MainActivity.DAY_PLAN_FRAG);
            intent.putExtra(MainActivity.ID_PARAM, trainingPlanDay.id);
            intent.putExtra(MainActivity.NAME_PARAM, "" + (dayNumber + 1));
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}