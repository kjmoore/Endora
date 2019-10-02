package com.kieranjohnmoore.endora.ui.dayplan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.endora.databinding.DayPlanItemBinding;
import com.kieranjohnmoore.endora.databinding.TrainingPlanItemBinding;
import com.kieranjohnmoore.endora.model.DayPlan;
import com.kieranjohnmoore.endora.model.TrainingPlan;
import com.kieranjohnmoore.endora.model.WeekWorkouts;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayPlanRecyclerView extends RecyclerView.Adapter<DayPlanRecyclerView.DayPlanViewHolder> {
    private final String TAG = DayPlanRecyclerView.class.getSimpleName();

    private List<DayPlan> dayPlans = Collections.emptyList();

    @NonNull
    @Override
    public DayPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final DayPlanItemBinding itemBinding =
                DayPlanItemBinding.inflate(layoutInflater, viewGroup, false);
        return new DayPlanViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DayPlanViewHolder viewHolder, int i) {
        viewHolder.bind(dayPlans.get(i));
    }

    @Override
    public int getItemCount() {
        return dayPlans.size();
    }

    void updateDayPlans(List<DayPlan> dayPlans) {
        Log.d(TAG, "Updating training plans");
        this.dayPlans = dayPlans;
        this.notifyDataSetChanged();
    }

    class DayPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        DayPlanItemBinding binding;
        DayPlan dayPlan;

        DayPlanViewHolder(DayPlanItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final DayPlan dayPlan) {
            this.dayPlan = dayPlan;
            binding.setDayPlan(dayPlan);
            //TODO: Work out Exercise list
            binding.setExerciseSummary("TEST");
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + dayPlan.day);
//            final Intent intent = new Intent(MainActivity.VIEW_ARTICLE);
//            intent.putExtra(MainActivity.ARTICLE_SELECTED, trainingPlan);
//            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}