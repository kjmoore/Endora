package com.kieranjohnmoore.endora.ui.trainingplanlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.endora.databinding.TrainingPlanItemBinding;
import com.kieranjohnmoore.endora.model.TrainingPlan;
import com.kieranjohnmoore.endora.model.WeekWorkouts;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrainingPlanListRecyclerView extends RecyclerView.Adapter<TrainingPlanListRecyclerView.TrainingPlanViewHolder> {
    private final String TAG = TrainingPlanListRecyclerView.class.getSimpleName();

    private List<TrainingPlan> trainingPlans = Collections.emptyList();

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
        viewHolder.bind(trainingPlans.get(i));
    }

    @Override
    public int getItemCount() {
        return trainingPlans.size();
    }

    void updateTrainingPlans(List<TrainingPlan> articles) {
        Log.d(TAG, "Updating training plans");
        this.trainingPlans = articles;
        this.notifyDataSetChanged();
    }

    class TrainingPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TrainingPlanItemBinding binding;
        TrainingPlan trainingPlan;

        TrainingPlanViewHolder(TrainingPlanItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final TrainingPlan trainingPlan) {
            this.trainingPlan = trainingPlan;
            binding.setProgram(trainingPlan);
            //TODO: Work out WeekWorkouts
            binding.setDays(new WeekWorkouts());
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + trainingPlan.name);
//            final Intent intent = new Intent(MainActivity.VIEW_ARTICLE);
//            intent.putExtra(MainActivity.ARTICLE_SELECTED, trainingPlan);
//            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}