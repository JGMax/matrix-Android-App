package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_alarms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager hoursManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        RecyclerView hoursList = findViewById(R.id.hours_list);
        hoursList.setLayoutManager(hoursManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(hoursList);

        TimeListAdapter hoursListAdapter = new TimeListAdapter(this, getTimeData(24));
        hoursList.setAdapter(hoursListAdapter);

        Date currentTime = Calendar.getInstance().getTime();
        hoursList.scrollToPosition(Integer.MAX_VALUE / 2 - 17 + currentTime.getHours());
    }

    @Override
    public void onBackPressed() {
        Intent output = new Intent();
        setResult(RESULT_OK, output);
        this.finish();
        super.onBackPressed();
    }

    private List<Integer> getTimeData(int maxSize)
    {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i<maxSize; i++)
            data.add(i);
        return data;
    }
}
