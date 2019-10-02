package com.kieranjohnmoore.endora.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.ui.trainingday.TrainingDayFragment;
import com.kieranjohnmoore.endora.ui.trainingplan.TrainingPlanFragment;
import com.kieranjohnmoore.endora.ui.trainingplanlist.TrainingPlanListFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String ID_PARAM = "com.kieranjohnmoore.endura.id";
    public static final String NAME_PARAM = "com.kieranjohnmoore.endura.name";
    public static final String DAY_PLAN_FRAG = "com.kieranjohnmoore.endura.day_plan_frag";
    public static final String TRAINING_PLAN_FRAG = "com.kieranjohnmoore.endura.training_plan_frag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TrainingPlanListFragment())
                    .commitNow();
        }

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(viewTrainingPlan, new IntentFilter(TRAINING_PLAN_FRAG));
        broadcastManager.registerReceiver(viewDayPlan, new IntentFilter(DAY_PLAN_FRAG));
    }

    private void setFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.container) == fragment) {
            return;
        }

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);

        fragmentTransaction.addToBackStack(null);

//        if (isTabletMode) {
//            mainBinding.selectArticle.setVisibility(View.INVISIBLE);
//        }
        fragmentTransaction.commit();
    }

    private BroadcastReceiver viewTrainingPlan = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to training plan");

            final Bundle bundle = intent.getExtras();
            Log.d(TAG, "Navigation bundle: " + bundle);

            if (bundle != null) {
                final Fragment fragment = new TrainingPlanFragment();
                fragment.setArguments(bundle);
                setFragment(fragment);
            } else {
                Log.e(TAG, "There was no bundle, so couldn't move to training plan");
            }

        }
    };

    private BroadcastReceiver viewDayPlan = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Navigating to training day");

            final Bundle bundle = intent.getExtras();
            Log.d(TAG, "Navigation bundle: " + bundle);

            if (bundle != null) {
                final Fragment fragment = new TrainingDayFragment();
                fragment.setArguments(bundle);
                setFragment(fragment);
            } else {
                Log.e(TAG, "There was no bundle, so couldn't move to training day");
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(viewTrainingPlan);
        broadcastManager.unregisterReceiver(viewDayPlan);
    }
}
