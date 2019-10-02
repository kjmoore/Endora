package com.kieranjohnmoore.endora.ui.dayplan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.databinding.TrainingDayFragmentBinding;
import com.kieranjohnmoore.endora.model.DayPlan;
import com.kieranjohnmoore.endora.ui.MainActivity;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DayPlanFragment extends Fragment {
    private static final String TAG = DayPlanFragment.class.getSimpleName();

    private final DayPlanRecyclerView recyclerView = new DayPlanRecyclerView();
    private TrainingDayFragmentBinding binding;
    private int trainingPlanId = -1;
    private String trainingPlanName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            trainingPlanId = getArguments().getInt(MainActivity.ID_PARAM);
            trainingPlanName = getArguments().getString(MainActivity.NAME_PARAM);
        } else {
            Log.e(TAG, "Couldn't get trainingPlanId");
        }

        final DayPlanViewModel.Factory factory = new DayPlanViewModel.Factory(requireActivity().getApplication(), trainingPlanId);

        final DayPlanViewModel viewModel = new ViewModelProvider(this, factory).get(DayPlanViewModel.class);
        viewModel.getDayPlans().observe(this, this::onPlansChanged);

        binding = DataBindingUtil.inflate(inflater, R.layout.training_day_fragment, container, false);
        binding.setPlanName(trainingPlanName);

        final Context context = getContext();
        if (context != null) {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.mainView.setLayoutManager(layoutManager);
            binding.mainView.setAdapter(recyclerView);
            binding.mainView.addItemDecoration(
                    new DividerItemDecoration(context, layoutManager.getOrientation()));
        }

        binding.fab.setOnClickListener(view -> {
            DayPlan test = new DayPlan();
            test.day = 1;
            test.trainingPlanId = trainingPlanId;

            AppDatabase.getExecutor().execute(() -> {
                AppDatabase.getInstance(getContext()).dayPlanDao().addDayPlan(test);
                Snackbar.make(view, "Added: " + test.day, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            });
        });
        return binding.getRoot();
    }

    private void onPlansChanged(List<DayPlan> dayPlans) {
        Log.e(TAG, "Training plans changed: " + Arrays.toString(dayPlans.toArray()));
        recyclerView.updateDayPlans(dayPlans);

        binding.progressBar.setVisibility(View.INVISIBLE);

        if (dayPlans.size() > 0) {
            binding.noData.setVisibility(View.INVISIBLE);
        }
    }
}
