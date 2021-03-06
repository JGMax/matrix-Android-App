package com.project.myapplication.barrelselector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.project.myapplication.TimeSelectorAdapter;

import java.util.ArrayList;

public class BarrelSelector extends SlowdownRecyclerView {
    private ArrayList<SlowdownRecyclerView> Barrels = new ArrayList<>();
    private ArrayList<SnapHelper> Helpers = new ArrayList<>();
    private final Context context;

    public BarrelSelector(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BarrelSelector(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BarrelSelector(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setRecyclerViews(Integer... ids) {
        for (int id : ids) {
            SlowdownRecyclerView view = findViewById(id);
            Barrels.add(view);
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(view);
            Helpers.add(snapHelper);
        }
    }

    public void setRecyclerViews(View... views) {
        for (View view : views) {
            Barrels.add((SlowdownRecyclerView) (view));
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView( (SlowdownRecyclerView) view);
            Helpers.add(snapHelper);
        }
    }

    private void setRecyclerViewSettings(final SlowdownRecyclerView view, final SnapHelper snapHelper, final Adapter adapter, final int centerCorrector) {
        view.setLayoutManager(new CenterZoomLayoutManager(context));

        //

        view.setAdapter(adapter);

        //final SnapHelper snapHelper = new LinearSnapHelper();
        //snapHelper.attachToRecyclerView(view);
        view.scrollToPosition(Integer.MAX_VALUE/2 + centerCorrector);

        view.post(new Runnable() {
            @Override
            public void run() {
                View mView = view.getLayoutManager().findViewByPosition(Integer.MAX_VALUE/2 + centerCorrector);
                if (mView == null) {
                    Log.e("Fuck", "Fuck");
                    return;
                }

                int[] snapDistance = snapHelper.calculateDistanceToFinalSnap(view.getLayoutManager(), mView);
                if (snapDistance != null && (snapDistance[0] != 0 || snapDistance[1] != 0))
                    view.scrollBy(snapDistance[0], snapDistance[1]);

            }
        });
    }

    public void setBarrelAdapter(int barrelNumber, Adapter adapter, int centerCorrector) {
        setRecyclerViewSettings(Barrels.get(barrelNumber), Helpers.get(barrelNumber), adapter, centerCorrector);
    }

    public void setBarrelData(int barrelNumber, ArrayList<String> data, int centerCorrector) {
        TimeSelectorAdapter adapter = new TimeSelectorAdapter(data);
        setRecyclerViewSettings(Barrels.get(barrelNumber), Helpers.get(barrelNumber), adapter, centerCorrector);
    }

    public void setBarrelData(int barrelNumber, int from, int to, int centerCorrector) {
        TimeSelectorAdapter adapter = new TimeSelectorAdapter(getArrayList(from, to));
        setRecyclerViewSettings(Barrels.get(barrelNumber), Helpers.get(barrelNumber), adapter, centerCorrector);
    }

    public ArrayList<View> getBarrelsSelectedItems() {
        ArrayList<View> selectedViews = new ArrayList<>();
        for (int i = 0; i < Barrels.size(); i++) {
            View view = Helpers.get(i).findSnapView(Barrels.get(i).getLayoutManager());
            selectedViews.add(view);
        }
        return selectedViews;
    }

    private int getPowCount(int num) {
        int powCounter = 0;
        if (num == 0) return 1;
        while (num != 0) {
            num /= 10;
            powCounter++;
        }
        return powCounter;
    }

    @SuppressLint("DefaultLocale")
    private ArrayList<String> getArrayList(int from, int to) {
        ArrayList<String> list = new ArrayList<>();
        int powers = getPowCount(to - 1);

        for (int i = from; i < to; i++) {
            StringBuilder zeros = new StringBuilder();
            for (int j = 0; j < powers - getPowCount(i); j++) zeros.append("0");

            list.add(String.format("%s%d", zeros.toString(), i));
        }
        return list;
    }

}
