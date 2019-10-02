package com.kieranjohnmoore.endora.ui.trainingday;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.database.AppDatabase;
import com.kieranjohnmoore.endora.databinding.TrainingDayFragmentBinding;
import com.kieranjohnmoore.endora.model.Exercise;
import com.kieranjohnmoore.endora.ui.MainActivity;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TrainingDayFragment extends Fragment {
    private static final String TAG = TrainingDayFragment.class.getSimpleName();

    private final TrainingDayRecyclerView recyclerView = new TrainingDayRecyclerView();
    private TrainingDayFragmentBinding binding;
    private int trainingDayId = -1;
    private String trainingDayName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            trainingDayId = getArguments().getInt(MainActivity.ID_PARAM);
            trainingDayName = getArguments().getString(MainActivity.NAME_PARAM);
        } else {
            Log.e(TAG, "Couldn't get trainingPlanId");
        }

        final TrainingDayViewModel.Factory factory = new TrainingDayViewModel.Factory(requireActivity().getApplication(), trainingDayId);

        final TrainingDayViewModel viewModel = new ViewModelProvider(this, factory).get(TrainingDayViewModel.class);
        viewModel.getExercises().observe(this, this::onExercisesChanged);

        binding = DataBindingUtil.inflate(inflater, R.layout.training_day_fragment, container, false);
        binding.setTitle(trainingDayName);

        final Context context = getContext();
        if (context != null) {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.mainView.setLayoutManager(layoutManager);
            binding.mainView.setAdapter(recyclerView);
            binding.mainView.addItemDecoration(
                    new DividerItemDecoration(context, layoutManager.getOrientation()));
        }

        binding.fab.setOnClickListener(view -> {
            final Exercise exercise = new Exercise();
            exercise.dayPlanId = trainingDayId;
            exercise.name = "Test Exercise";

            AppDatabase.getExecutor().execute(() -> {
                AppDatabase.getInstance(getContext()).exerciseDao().addExercise(exercise);
                Snackbar.make(view, "Added: " + exercise.id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            });
        });

        final MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            setHasOptionsMenu(true);
            activity.setSupportActionBar(binding.appbar.toolbar);
            final ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void goBack() {
        final MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            case R.id.done:
                goBack();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onExercisesChanged(List<Exercise> exercises) {
        Log.e(TAG, "Training plans changed: " + Arrays.toString(exercises.toArray()));
        recyclerView.updateExercises(exercises);

        binding.progressBar.setVisibility(View.INVISIBLE);

        if (exercises.size() > 0) {
            binding.noData.setVisibility(View.INVISIBLE);
        }
    }
}
