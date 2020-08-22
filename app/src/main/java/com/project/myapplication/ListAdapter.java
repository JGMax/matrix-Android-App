package com.project.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<String> alarms;
    private List<Boolean> isActive;
    private Context context;

    public ListAdapter(Context contextI, List<String> alarms1, List<Boolean> isActive1) {
        context = contextI;
        alarms = alarms1;
        isActive = isActive1;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).
                inflate(R.layout.list_item, parent, false);

        TextView timeAlarm = rowView.findViewById(R.id.timeAlarm);

        timeAlarm.setText(alarms.get(position));
        Switch alarmActive = rowView.findViewById(R.id.alarmActive);
        alarmActive.setChecked(isActive.get(position));

        if(isActive.get(position))
        {
            timeAlarm.setTextColor(context.getColor(R.color.choseAlarm));
        }
        else
        {
            timeAlarm.setTextColor(context.getColor(R.color.colorAccent));
        }

        return rowView;
    }
}