package com.m.moviememoir.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.m.moviememoir.Bean.Cinema;
import com.m.moviememoir.R;

import java.util.List;

public class CinemaAdapter extends BaseAdapter implements SpinnerAdapter {
    private LayoutInflater mInflater;
    private List<Cinema> mDatas;

    public CinemaAdapter(Context context, List<Cinema> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_cinema, null);
        TextView title = (TextView) convertView.findViewById(R.id.tvName);
        TextView postcode = (TextView) convertView.findViewById(R.id.tvPostCode);
        title.setText(mDatas.get(position).getCinemaname());
        postcode.setText(mDatas.get(position).getPostcode() + "");
        return convertView;
    }

    @Override
    public View getDropDownView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_cinema, null);
        TextView title = (TextView) convertView.findViewById(R.id.tvName);
        TextView postcode = (TextView) convertView.findViewById(R.id.tvPostCode);
        title.setText(mDatas.get(position).getCinemaname());
        postcode.setText(mDatas.get(position).getPostcode() + "");
        return convertView;
    }

//    public interface SpinnerOnClickListener {
//        void getCinema(Cinema cinema);
//    }
//
//    public SpinnerOnClickListener spinnerOnClickListener = null;
//
//    public void setSpinnerOnClickListener(SpinnerOnClickListener spinnerOnClickListener) {
//        this.spinnerOnClickListener = spinnerOnClickListener;
//    }
}