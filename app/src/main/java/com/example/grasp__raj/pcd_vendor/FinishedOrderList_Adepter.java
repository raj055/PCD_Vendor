package com.example.grasp__raj.pcd_vendor;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class FinishedOrderList_Adepter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private List<PurchaseData> purchaseData;
    private boolean isListView;
    private SparseBooleanArray mSelectedItemsIds;

    public FinishedOrderList_Adepter(Context context, List<PurchaseData> purchaseData, FinishedOrder listener) {
        this.activity = activity;
        this.purchaseData = purchaseData;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public int getCount() { return purchaseData.size(); }

    @Override
    public Object getItem(int location) {
        return purchaseData.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_po_layout, null);


        TextView name = (TextView) convertView.findViewById(R.id.textViewName);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

        // getting movie data for the row
        PurchaseData m = purchaseData.get(position);

        // title
        name.setText(m.getName());
        checkBox.setChecked(mSelectedItemsIds.get(position));

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox(position, !mSelectedItemsIds.get(position));
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox(position, !mSelectedItemsIds.get(position));
            }
        });

        return convertView;
    }

    public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}