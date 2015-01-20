package com.pc.example.firsttodoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by priyam on 1/19/15.
 */
public class TodoItemAdapter extends ArrayAdapter<TodoItem> {


    public TodoItemAdapter(Context context, ArrayList<TodoItem> items) {
        super(context,0,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todoitem, parent, false);
        }
        // Lookup view for data population
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        // Populate the data into the template view using the data object
        tvBody.setText(item.getBody());
        // Return the completed view to render on screen
        return convertView;
    }
}
