package com.applicationcommunity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListItem> data;
    private int resource;

    ListAdapter(Context context,
                  ArrayList<ListItem> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    public String getItemKey(int position){
        ListItem item = (ListItem) getItem(position);
        return item.getKey();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Activity activity = (Activity)context;
        ListItem item = (ListItem) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(resource, null);
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(item.getTitle());
        ((TextView) convertView.findViewById(R.id.date)).setText(item.getDate());
        ((TextView) convertView.findViewById(R.id.detail)).setText(item.getDetail());
        ((TextView) convertView.findViewById(R.id.key)).setText(item.getKey());

        return convertView;
    }

}
