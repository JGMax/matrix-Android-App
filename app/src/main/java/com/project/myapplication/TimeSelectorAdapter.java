package com.project.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeSelectorAdapter extends RecyclerView.Adapter<TimeSelectorAdapter.TimeSelectorViewHolder> {
    private ArrayList<String> time;

    public TimeSelectorAdapter(ArrayList<String> time) {
        this.time = time;
    }

    public class TimeSelectorViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public TimeSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.timeSelectorNum);
        }
    }

    @NonNull
    @Override
    public TimeSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_selector_item, parent, false);
        return new TimeSelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSelectorViewHolder holder, int position) {
        /*if (position < 2)
            holder.text.setText("  ");
        else*/
            holder.text.setText(time.get((position) % time.size()));

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
