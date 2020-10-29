package com.project.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Date;

public class TimeSelectorDialog extends DialogFragment {
    private Button soundButton;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setView(R.layout.dialog_time_selector).show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_time_selector, container, false);
        Date date = new Date();
        soundButton = v.findViewById(R.id.soundButton);

        final SlowdownRecyclerView hoursSelector = v.findViewById(R.id.hoursSelector);
        final SlowdownRecyclerView minutesSelector = v.findViewById(R.id.minutesSelector);

        final BarrelSelector barrelSelector = new BarrelSelector(v.getContext());

        barrelSelector.setRecyclerViews(hoursSelector, minutesSelector);
        barrelSelector.setBarrelData(0, 0, 24, date.getHours() - 15);
        barrelSelector.setBarrelData(1, 0, 60, date.getMinutes() - 3);

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("First Barrel", barrelSelector.getBarrelsSelectedItemStringData().get(0));
                Log.e("Second Barrel", barrelSelector.getBarrelsSelectedItemStringData().get(1));
            }
        });

        /*hoursSelector.setLayoutManager(new CenterZoomLayoutManager(v.getContext()));

        TimeSelectorAdapter adapter = new TimeSelectorAdapter(getArrayList(0, 24));
        hoursSelector.setAdapter(adapter);
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(hoursSelector);
        hoursSelector.scrollToPosition(Integer.MAX_VALUE/2 + date.getHours() - 15);

        hoursSelector.post(new Runnable() {
            @Override
            public void run() {
                View view = hoursSelector.getLayoutManager().findViewByPosition(Integer.MAX_VALUE/2 + date.getHours() - 15);
                if (view == null) {
                    Log.e("fuck", "fuck");
                    return;
                }

                int[] snapDistance = snapHelper.calculateDistanceToFinalSnap(hoursSelector.getLayoutManager(), view);
                if (snapDistance != null && (snapDistance[0] != 0 || snapDistance[1] != 0))
                    hoursSelector.scrollBy(snapDistance[0], snapDistance[1]);
            }
        });*/


        return v;
    }
}
