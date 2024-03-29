package com.wht.rishiherherbocare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wht.rishiherherbocare.Object.ComplaintObject;
import com.wht.rishiherherbocare.R;

import java.util.ArrayList;

public class ComplaintGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ComplaintObject> attendenceGridDialogObjArrayList;

    public ComplaintGridAdapter(Context c, ArrayList<ComplaintObject> attendenceGridDialogObjArrayList) {
        mContext = c;
        this.attendenceGridDialogObjArrayList = attendenceGridDialogObjArrayList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return attendenceGridDialogObjArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.complaint_adapter_grid_row_items, null);
            TextView textView = (TextView) grid.findViewById(R.id.tvSymptom);
            textView.setText(attendenceGridDialogObjArrayList.get(position).complaint);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}