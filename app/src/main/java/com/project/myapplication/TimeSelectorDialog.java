package com.project.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.project.myapplication.barrelselector.BarrelSelector;
import com.project.myapplication.barrelselector.SlowdownRecyclerView;

import java.util.Date;

public class TimeSelectorDialog extends DialogFragment {

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
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Date date = new Date();

        ToggleButton soundButton = v.findViewById(R.id.soundButton);
        ToggleButton vibrationButton = v.findViewById(R.id.vibrationButton);
        ToggleButton sunriseButton = v.findViewById(R.id.sunriseButton);

        Button confirmTimeButton = v.findViewById(R.id.confirmTimeButton);

        final SlowdownRecyclerView hoursSelector = v.findViewById(R.id.hoursSelector);
        final SlowdownRecyclerView minutesSelector = v.findViewById(R.id.minutesSelector);

        final BarrelSelector barrelSelector = new BarrelSelector(v.getContext());

        barrelSelector.setRecyclerViews(hoursSelector, minutesSelector);
        barrelSelector.setBarrelData(0, 0, 24, date.getHours() - 15);
        barrelSelector.setBarrelData(1, 0, 60, date.getMinutes() - 3);

        confirmTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setTint(getResources().getColor(R.color.selectedTimeSelectorButtonColor));
            }
        });

        soundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.ic_volume_up));
                    compoundButton.getBackground().setTint(getResources().getColor(R.color.selectedTimeSelectorButtonColor));
                }
                else {
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.ic_volume_off));
                    compoundButton.getBackground().setTint(getResources().getColor(R.color.unselectedTimeSelectorButtonColor));
                }
            }
        });

        vibrationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.ic_vibration_on));
                    compoundButton.getBackground().setTint(getResources().getColor(R.color.selectedTimeSelectorButtonColor));
                }
                else {
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.ic_vibration_on));
                    compoundButton.getBackground().setTint(getResources().getColor(R.color.unselectedTimeSelectorButtonColor));
                }
            }
        });


        sunriseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.ic_sunny_on));
                    compoundButton.getBackground().setTint(getResources().getColor(R.color.selectedTimeSelectorButtonColor));
                }
                else {
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.ic_sunny_on));
                    compoundButton.getBackground().setTint(getResources().getColor(R.color.unselectedTimeSelectorButtonColor));
                }
            }
        });


        return v;
    }
}
