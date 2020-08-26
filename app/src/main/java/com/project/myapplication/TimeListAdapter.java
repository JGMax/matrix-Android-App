package com.project.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeListAdapter extends RecyclerView.Adapter<TimeListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Integer> data;

    TimeListAdapter(Context context, List<Integer> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public TimeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.time_list_item, parent, false);
        return new TimeListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TimeListAdapter.ViewHolder holder, int position) {
        int actualPosition = position % data.size();
        Log.e(Integer.toString(position), Float.toString(holder.itemView.getY()));
        int time = data.get(actualPosition);
        if(time >= 10)
            holder.timeView.setText(Integer.toString(time));
        else
            holder.timeView.setText("0" + time);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

   public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView timeView;
        ViewHolder(View view){
            super(view);
            timeView = view.findViewById(R.id.time);
        }
    }
}
