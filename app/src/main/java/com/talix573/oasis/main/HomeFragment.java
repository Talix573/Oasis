package com.talix573.oasis.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.talix573.oasis.R;
import com.talix573.oasis.common.CustomAdapter;
import com.talix573.oasis.common.Plant;

import java.util.List;

public class HomeFragment extends Fragment {

    private GridView gridView;
    private List<Plant> plants;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridView = getView().findViewById(R.id.grid_view);
//        CustomAdapter customAdapter = new CustomAdapter(this, plants);
//        gridView.setAdapter(customAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Action to inititate once user clicks on plant portrait card
//            }
//        });

        super.onViewCreated(view, savedInstanceState);
    }
}
