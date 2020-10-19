package com.project.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
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
        final SlowdownRecyclerView hoursSelector = v.findViewById(R.id.hoursSelector);

        hoursSelector.setLayoutManager(new CenterZoomLayoutManager(v.getContext()));

        TimeSelectorAdapter adapter = new TimeSelectorAdapter(getArrayListForHours());
        hoursSelector.setAdapter(adapter);
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(hoursSelector);
        hoursSelector.scrollToPosition(2);

        hoursSelector.post(new Runnable() {
            @Override
            public void run() {
                View view  = hoursSelector.getLayoutManager().findViewByPosition(2);
                if (view == null) {
                    return;
                }

                int [] snapDistance = snapHelper.calculateDistanceToFinalSnap(hoursSelector.getLayoutManager(), view);
                if (snapDistance != null && (snapDistance[0] != 0 || snapDistance[1] != 0))
                    hoursSelector.scrollBy(snapDistance[0], snapDistance[1]);
            }
        });


        return v;
    }



    @SuppressLint("DefaultLocale")
    private ArrayList<String> getArrayListForHours() {
        ArrayList<String> list = new ArrayList<>();
        Date date = new Date();
        for (int i = date.getHours(); i < 24 + date.getHours(); i++) {
            if (i%24 < 10)
                list.add(String.format("0%d", i%24));
            else
                list.add(Integer.toString(i%24));
        }
        return list;
    }
}
