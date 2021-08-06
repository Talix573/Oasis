package com.talix573.oasis.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.talix573.oasis.R;

import java.time.Instant;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Plant[] plants;
    private Resources res;
    private OnItemClickListener onItemClickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView plantName;
        private final TextView hydrationAverage;
        private final ImageView plantImg;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            plantName = (TextView) view.findViewById(R.id.item_plant_name);
            hydrationAverage = (TextView) view.findViewById(R.id.item_hydration_level);
            plantImg = (ImageView) view.findViewById(R.id.item_plant_image);
        }

        public TextView getPlantNameView() {
            return plantName;
        }

        public TextView getPlantHydrationLevelView() {
            return hydrationAverage;
        }

        public ImageView getImageView() {
            return plantImg;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet Plant[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(Plant[] dataSet, OnItemClickListener onItemClickListener) {
        plants = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plant_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        this.res = viewHolder.itemView.getContext().getResources();
        return viewHolder;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position, Plant plant);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getPlantNameView().setText(plants[position].name);
        Date newWateredDate = new Date();
        // Acquire time before since last irrigation. Have the irrigation period divided by the time since to get hydration level
        Long irrgationPeriodDouble = plants[position].irrigationPeriod;
        Long timeIntervalBetweenLastWatered = newWateredDate.getTime() - plants[position].lastWatered.getTime();
        Double hydrationLevel = (100 - (timeIntervalBetweenLastWatered * 100.0 / irrgationPeriodDouble));

        viewHolder.getPlantNameView().setText(plants[position].name);
        viewHolder.getPlantHydrationLevelView().setText(String.format("%.0f", hydrationLevel) + "% ");
        if (hydrationLevel < 30) {
            viewHolder.getPlantHydrationLevelView().setTextColor(res.getColor(R.color.hydrationBad));
        } else if (hydrationLevel >= 30 && hydrationLevel < 70) {
            viewHolder.getPlantHydrationLevelView().setTextColor(res.getColor(R.color.hydrationAverage));
        } else {
            viewHolder.getPlantHydrationLevelView().setTextColor(res.getColor(R.color.hydrationGood));
        }
        Bitmap bitmap = BitmapFactory.decodeFile(plants[position].imagePath);
        viewHolder.getImageView().setImageBitmap(bitmap);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClicked(viewHolder.getAdapterPosition(), plants[viewHolder.getAdapterPosition()]);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return plants.length;
    }
}
