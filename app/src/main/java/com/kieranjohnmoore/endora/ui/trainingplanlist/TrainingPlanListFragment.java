package com.kieranjohnmoore.endora.ui.trainingplanlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.databinding.TrainingPlanListFragmentBinding;
import com.kieranjohnmoore.endora.model.TrainingPlan;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class TrainingPlanListFragment extends Fragment {
    private static final String TAG = TrainingPlanListFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final TrainingPlanListFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.training_plan_list_fragment, container, false);

        final AppCompatActivity mainActivity = (AppCompatActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setSupportActionBar(binding.toolbar);
        }
        binding.fab.setOnClickListener(view -> {
            TrainingPlan test = new TrainingPlan();
            test.name = Double.toString(Math.random());

            AppDatabase.getExecutor().execute(() -> {
                AppDatabase.getInstance(getContext()).trainingPlanDao().addTrainingPlan(test);
                Snackbar.make(view, "Added: " + test.name, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            });
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TrainingPlanViewModel viewModel
                = ViewModelProviders.of(this).get(TrainingPlanViewModel.class);
        viewModel.getTrainingPlans().observe(this, this::onTrainingPlansChanged);
    }

    private void onTrainingPlansChanged(List<TrainingPlan> trainingPlans) {
        Log.e(TAG, "Training plans changed: " + Arrays.toString(trainingPlans.toArray()));
    }
}
