package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import MyViews.StepProgressBar;

public class MainActivity extends AppCompatActivity
{

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        TextView a;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StepProgressBar stepProgressBar = findViewById(R.id.stepProgressBar);

        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, stepProgressBar.getValue());
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int animatedValue = (int)valueAnimator.getAnimatedValue();
                stepProgressBar.setValue(animatedValue);
            }
        });
        valueAnimator.start();

    }
}