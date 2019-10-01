package com.kieranjohnmoore.endora.ui;

import android.os.Bundle;

import com.kieranjohnmoore.endora.R;
import com.kieranjohnmoore.endora.ui.trainingplanlist.TrainingPlanListFragment;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TrainingPlanListFragment())
                    .commitNow();
        }
    }
}
