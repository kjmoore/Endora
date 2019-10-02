package com.kieranjohnmoore.endora.ui.trainingplanlist;

import android.content.Context;
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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TrainingPlanListFragment extends Fragment {
    private static final String TAG = TrainingPlanListFragment.class.getSimpleName();

    private final TrainingPlanListRecyclerView recyclerView = new TrainingPlanListRecyclerView();
    private TrainingPlanListFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.training_plan_list_fragment, container, false);
        binding.setTitle(getString(R.string.app_name));

        final Context context = getContext();
        if (context != null) {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.mainView.setLayoutManager(layoutManager);
            binding.mainView.setAdapter(recyclerView);
            binding.mainView.addItemDecoration(
                    new DividerItemDecoration(context, layoutManager.getOrientation()));
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
        final TrainingPlanListViewModel viewModel
                = ViewModelProviders.of(this).get(TrainingPlanListViewModel.class);
        viewModel.getTrainingPlans().observe(this, this::onTrainingPlansChanged);
    }

    private void onTrainingPlansChanged(List<TrainingPlan> trainingPlans) {
        Log.v(TAG, "Training plans changed: " + Arrays.toString(trainingPlans.toArray()));
        recyclerView.updateTrainingPlans(trainingPlans);

        binding.progressBar.setVisibility(View.INVISIBLE);

        if (trainingPlans.size() > 0) {
            binding.noData.setVisibility(View.INVISIBLE);
        }
    }
}
