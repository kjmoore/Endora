package com.kieranjohnmoore.endora.ui.workout;

import android.Manifest;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.databinding.WorkoutFragmentBinding;
import com.kieranjohnmoore.endora.ui.widget.TrackerWidget;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class WorkoutFragment extends Fragment {
    private static final String TAG = WorkoutFragment.class.getSimpleName();
    private static final int PERMISSION_ACTIVITY_RECONITION = 1;

    private WorkoutFragmentBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.workout_fragment, container, false);
        binding.setTitle(getString(R.string.workout_now));

        binding.pushToFit.setOnClickListener((view) -> {
            Log.d(TAG, "Sending workout to google fit");

            Activity activity = getActivity();
            if (activity != null) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACTIVITY_RECOGNITION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission was not granted");
                    String[] permissions = new String[1];
                    permissions[0] = Manifest.permission.ACTIVITY_RECOGNITION;
                    ActivityCompat.requestPermissions(activity, permissions, PERMISSION_ACTIVITY_RECONITION);
                } else {
                    Log.d(TAG, "Permission was granted, uploading");
                    uploadData();
                }
            }

            updateWidget();

        });

        return binding.getRoot();
    }

    private void uploadData() {
        if (getActivity() == null) {
            Log.w(TAG, "Could not get activity");
            return;
        }

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        int mins = Integer.parseInt(binding.workoutTime.getText().toString());
        mins = -mins;
        cal.add(Calendar.MINUTE, mins);
        long startTime = cal.getTimeInMillis();

        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(getActivity())
                        .setDataType(DataType.AGGREGATE_WEIGHT_SUMMARY)
                        .setStreamName(TAG + " - weightlifting")
                        .setType(DataSource.TYPE_RAW)
                        .build();

        int reps = 100;
        DataSet dataSet = DataSet.create(dataSource);
        DataPoint dataPoint =
                dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_REPETITIONS).setInt(reps);
        dataPoint.getValue(Field.FIELD_STEPS).setInt(reps);
        dataSet.add(dataPoint);

        try {
            Task<Void> response = Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity())).insertData(dataSet);
            Log.d(TAG, response.getResult().toString());
        } catch (NullPointerException e) {
            Log.d(TAG, "Could not get response " + e.getMessage());
        }
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(Objects.requireNonNull(getContext()), TrackerWidget.class));

        //This is just random data for now, should be generated from the database once workouts are stored there
        boolean[] successFulDays = {randomBoolean(), randomBoolean(), randomBoolean(), randomBoolean(), randomBoolean(), randomBoolean(), randomBoolean()};

        if (appWidgetIds.length > 0) {
            TrackerWidget.updateAppWidget(Objects.requireNonNull(getContext()),
                    appWidgetManager, appWidgetIds, successFulDays);
        }
    }

    private boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ACTIVITY_RECONITION) {
            if (this.getView() != null) {
                Snackbar.make(getView(), getString(R.string.permission_approved), Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
