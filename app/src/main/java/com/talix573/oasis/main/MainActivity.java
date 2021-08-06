package com.talix573.oasis.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.talix573.oasis.R;
import com.talix573.oasis.common.AppDatabase;
import com.talix573.oasis.common.Plant;
import com.talix573.oasis.common.PlantDao;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private AppDatabase db;
    private PlantDao plantDao;
    private List<Plant> plants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize Home Fragment when activity first started
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.fragment_container);
        }

        // Database
        setupDatabase();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handles which menu item in navigation drawer is pressed
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Work In Progress", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                // Logout of Firebase Auth
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setupDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "plant").build();
        plantDao = db.plantDao();
        getPlants();
    }

    public List<Plant> getPlants() {
        this.plants = plantDao.getAll().getValue();
        return plants;
    }

    @Override
    public void onBackPressed() {
        // Back button closes the navigation drawer if opened
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

