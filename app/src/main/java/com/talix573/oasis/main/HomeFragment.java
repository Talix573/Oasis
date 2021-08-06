package com.talix573.oasis.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.talix573.oasis.R;
import com.talix573.oasis.common.AppDatabase;
import com.talix573.oasis.common.CustomAdapter;
import com.talix573.oasis.common.Plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    // Constants
    static final int NEW_PLANT = 1;
    static final int EXISTING_PLANT = 2;

    // UI
    private FloatingActionButton addPlantB;
    private ImageView headerIcon;
    private TextView headerText;
    private TextView emptyNotice;
    private Button waterPlantB;

    // Containers
    private List<Plant> selectedPlants = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppDatabase db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "plant").allowMainThreadQueries().fallbackToDestructiveMigration().build();

         RecyclerView recyclerView = getView().findViewById(R.id.recycler_grid_view);
        headerIcon = view.findViewById(R.id.header_icon);
        headerText = view.findViewById(R.id.header_text);
        emptyNotice = view.findViewById(R.id.empty_notice);

        addPlantB = view.findViewById(R.id.add_plant_button);
        addPlantB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addNewPlantIntent = new Intent(getActivity(), AddPlantActivity.class);
                addNewPlantIntent.putExtra("PURPOSE", NEW_PLANT);
                startActivity(addNewPlantIntent);
            }
        });

        waterPlantB = view.findViewById(R.id.water_button);
        waterPlantB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Plant selectedPlant : selectedPlants) {
                    db.plantDao().updatePlantLastWatered(new Date(), selectedPlant.id);
                    Log.i("PLANT DETAIL", selectedPlant.name);
                }
                selectedPlants.clear();
            }
        });

        Plant[] plants = db.plantDao().getAll();

        if (plants.length == 0) {
            headerIcon.setVisibility(View.VISIBLE);
            headerText.setVisibility(View.VISIBLE);
            emptyNotice.setVisibility(View.VISIBLE);
        } else {
            headerIcon.setVisibility(View.INVISIBLE);
            headerText.setVisibility(View.INVISIBLE);
            emptyNotice.setVisibility(View.INVISIBLE);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        CustomAdapter customAdapter = new CustomAdapter(plants, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, Plant plant) {
                if (selectedPlants.indexOf(plant) == -1) {
                    selectedPlants.add(plant);
                } else {
                    selectedPlants.remove(plant);
                }

            }
        });
        recyclerView.setAdapter(customAdapter);

        super.onViewCreated(view, savedInstanceState);
    }
}
