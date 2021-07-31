package com.talix573.oasis.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Plant> plants;
    private LayoutInflater inflater;
    public CustomAdapter(Context applicationContext, List<Plant> plants) {
        this.context = applicationContext;
        this.plants = plants;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
