package com.example.shubham.onlineattendance;

/**
 * Created by shubham on 19/03/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    Context c;
    ArrayList<Spacecraft> spacecrafts;
    LayoutInflater inflater;
    String address;

    public CustomAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
        //INITIALIE
        inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return spacecrafts.get(position).getRoll();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.list_checkitem, parent, false);


        }

        TextView Roll = (TextView) convertView.findViewById(R.id.RollNo);
        TextView fname = (TextView) convertView.findViewById(R.id.FirstName);
        TextView lname = (TextView) convertView.findViewById(R.id.LastName);
                Roll.setText(spacecrafts.get(position).getRolll());
                fname.setText(spacecrafts.get(position).getName());
                lname.setText(spacecrafts.get(position).getLname());
                        //ITEM CLICKS
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(c, spacecrafts.get(position).getRolll(), Toast.LENGTH_SHORT).show();
                    }
                });

        return convertView;
    }
}
