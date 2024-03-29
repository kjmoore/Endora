package com.kieranjohnmoore.endora.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.databinding.ActivityMainBinding;
import com.kieranjohnmoore.endora.ui.exerciseset.ExerciseSetFragment;
import com.kieranjohnmoore.endora.ui.trainingday.TrainingDayFragment;
import com.kieranjohnmoore.endora.ui.trainingplan.TrainingPlanFragment;
import com.kieranjohnmoore.endora.ui.trainingplanlist.TrainingPlanListFragment;
import com.kieranjohnmoore.endora.ui.workout.WorkoutFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String ID_PARAM = "com.kieranjohnmoore.endura.id";
    public static final String DAY_ID_PARAM = "com.kieranjohnmoore.endura.day_id";
    public static final String TRAINING_PLAN_ID = "com.kieranjohnmoore.endura.plan_id";
    public static final String NAME_PARAM = "com.kieranjohnmoore.endura.name";
    public static final String DAY_PLAN_FRAG = "com.kieranjohnmoore.endura.day_plan_frag";
    public static final String TRAINING_PLAN_FRAG = "com.kieranjohnmoore.endura.training_plan_frag";
    public static final String EXERCISE_SET_FRAG = "com.kieranjohnmoore.endura.exercise_set_frag";
    public static final String WORKOUT_FRAG = "com.kieranjohnmoore.endura.workout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TrainingPlanListFragment())
                    .commitNow();
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(viewTrainingPlan, new IntentFilter(TRAINING_PLAN_FRAG));
        broadcastManager.registerReceiver(viewDayPlan, new IntentFilter(DAY_PLAN_FRAG));
        broadcastManager.registerReceiver(viewExerciseSet, new IntentFilter(EXERCISE_SET_FRAG));
        broadcastManager.registerReceiver(viewWorkout, new IntentFilter(WORKOUT_FRAG));
    }

    private void setFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.container) == fragment) {
            return;
        }

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void getDataAndSetFragment(Intent intent, Fragment fragment) {
        final Bundle bundle = intent.getExtras();
        Log.d(TAG, "Navigation bundle: " + bundle);

        if (bundle != null) {
            fragment.setArguments(bundle);
            setFragment(fragment);
        } else {
            Log.e(TAG, "There was no bundle, so couldn't move");
        }
    }

    private BroadcastReceiver viewTrainingPlan = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to training plan");
            final Fragment fragment = new TrainingPlanFragment();
            getDataAndSetFragment(intent, fragment);
        }
    };

    private BroadcastReceiver viewDayPlan = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to training day");
            final Fragment fragment = new TrainingDayFragment();
            getDataAndSetFragment(intent, fragment);
        }
    };

    private BroadcastReceiver viewExerciseSet = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to exercise set");
            final Fragment fragment = new ExerciseSetFragment();
            getDataAndSetFragment(intent, fragment);
        }
    };

    private BroadcastReceiver viewWorkout = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to exercise set");
            final Fragment fragment = new WorkoutFragment();
            getDataAndSetFragment(intent, fragment);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(viewTrainingPlan);
        broadcastManager.unregisterReceiver(viewDayPlan);
        broadcastManager.unregisterReceiver(viewExerciseSet);
        broadcastManager.unregisterReceiver(viewWorkout);
    }
}
