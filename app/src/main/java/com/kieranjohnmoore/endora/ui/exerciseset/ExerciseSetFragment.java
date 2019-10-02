package com.kieranjohnmoore.endora.ui.exerciseset;

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
import com.kieranjohnmoore.endora.databinding.ExerciseSetFragmentBinding;
import com.kieranjohnmoore.endora.model.Exercise;
import com.kieranjohnmoore.endora.model.ExerciseSet;
import com.kieranjohnmoore.endora.model.TrainingPlan;
import com.kieranjohnmoore.endora.ui.MainActivity;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ExerciseSetFragment extends Fragment {
    private static final String TAG = ExerciseSetFragment.class.getSimpleName();

    private final ExerciseSetRecyclerView recyclerView = new ExerciseSetRecyclerView();
    private ExerciseSetFragmentBinding binding;
    private int exerciseId = -1;
    private int dayplayId = -1;
    private List<ExerciseSet> exerciseSets = Collections.emptyList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            exerciseId = getArguments().getInt(MainActivity.ID_PARAM);
            dayplayId = getArguments().getInt(MainActivity.DAY_ID_PARAM);
        } else {
            Log.e(TAG, "Couldn't get ID");
        }

        final ExerciseSetViewModel.Factory factory = new ExerciseSetViewModel.Factory(requireActivity().getApplication(), exerciseId);

        final ExerciseSetViewModel viewModel = new ViewModelProvider(this, factory).get(ExerciseSetViewModel.class);
        viewModel.getSets().observe(this, this::onExercisesChanged);
        viewModel.getExercise().observe(this, this::onExerciseChanged);

        binding = DataBindingUtil.inflate(inflater, R.layout.exercise_set_fragment, container, false);

        final Context context = getContext();
        if (context != null) {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.mainView.setLayoutManager(layoutManager);
            binding.mainView.setAdapter(recyclerView);
            binding.mainView.addItemDecoration(
                    new DividerItemDecoration(context, layoutManager.getOrientation()));
        }

        binding.fab.setOnClickListener(view -> {
            final ExerciseSet exerciseSet = new ExerciseSet();
            exerciseSet.exercisesId = exerciseId;

            AppDatabase.getExecutor().execute(() -> {
                AppDatabase.getInstance(getContext()).exerciseSetDao().addExerciseSet(exerciseSet);
                Snackbar.make(view, "Added: " + exerciseSet.id, Snackbar.LENGTH_LONG)
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
                goBack();
                break;
            case R.id.done:
                AppDatabase.getExecutor().execute(() -> {
                    final Exercise exercise = new Exercise();
                    exercise.id = exerciseId;
                    exercise.dayPlanId = dayplayId;
                    exercise.name = binding.selectedExercise.toString();
                    exercise.restBetweenSets = Integer.parseInt(binding.restTime.getText().toString());
                    AppDatabase.getInstance(getContext()).exerciseDao().updateExercise(exercise);
                    for (ExerciseSet set : exerciseSets) {
                        AppDatabase.getInstance(getContext()).exerciseSetDao().updateExerciseSet(set);
                    }
                });
                goBack();
                break;
            case R.id.delete:
                AppDatabase.getExecutor().execute(() -> {
                    final Exercise exercise = new Exercise();
                    exercise.id = exerciseId;
                    AppDatabase.getInstance(getContext()).exerciseDao().deleteExercie(exercise);
                });
                goBack();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onExercisesChanged(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
        recyclerView.updateExercises(exerciseSets);

        binding.progressBar.setVisibility(View.INVISIBLE);

        if (exerciseSets.size() > 0) {
            binding.noData.setVisibility(View.INVISIBLE);
        }
    }

    private void onExerciseChanged(Exercise exercise) {
        binding.restTime.setText(String.format(Locale.getDefault(), "%d", exercise.restBetweenSets));
        binding.setTitle(exercise.name);
        //TODO: generate this list and fill it
//        binding.selectedExercise.setSelection(1);
    }
}
