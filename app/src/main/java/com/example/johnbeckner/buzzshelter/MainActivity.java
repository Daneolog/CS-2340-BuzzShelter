package com.example.johnbeckner.buzzshelter;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Shelter> filteredList;

    private ListView ShelterLV;
    private Button Filter;
    private Button Map;
    private File file;
    private BinarySerialize bs;

    @SuppressWarnings({"FeatureEnvy", "OverlyLongMethod"}) // Fixing this warning would require us to re-write ShelterList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ShelterLV = findViewById(R.id.List);
        Filter = findViewById(R.id.FilterButton);
        filteredList = new ArrayList<>();
        file = new File(this.getFilesDir(), "data.bin");
        Map = findViewById(R.id.Map);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if ((extras != null) && extras.containsKey("Filtered Shelter List")) {
            filteredList = extras.getParcelableArrayList("Filtered Shelter List");
        } else {
            Resources resources = this.getResources();
            ShelterList.parseDatabase(resources.openRawResource(R.raw.shelterdatabase));
            filteredList = ShelterList.getShelters();
        }

        bs = new BinarySerialize(ShelterList.getShelters(),
                ShelterList.getFilteredList(),
                Auth.getUsers());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder logoutAlt = new AlertDialog.Builder(MainActivity.this);
            logoutAlt.setTitle("Logout?");
            logoutAlt.setMessage("Do you want to log out?");
            logoutAlt.setCancelable(false);
            logoutAlt.setPositiveButton("Yes", (dialogInterface, i) -> {
                bs.saveBinary(file);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            });
            logoutAlt.setNegativeButton("No", (dialogInterface, i) -> {
                // dismiss the alert
            });
            AlertDialog alert = logoutAlt.create();
            alert.show();
        });

        ListAdapter arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                filteredList
        );
        ShelterLV.setAdapter(arrayAdapter);

        ShelterLV.setOnItemClickListener((adapterView, view, i, l) -> {
            Shelter pressed = (Shelter) ShelterLV.getItemAtPosition(i);
            Intent intent13 = new Intent(getBaseContext(), ShelterInfoActivity.class);
            intent13.putExtra("shelter_info", (Parcelable) pressed);
            startActivity(intent13);
        });

        Filter.setOnClickListener(view -> {
            Intent intent12 = new Intent(getBaseContext(), SearchShelterActivity.class);
            intent12.putExtra("shelter_list", filteredList);
            startActivity(intent12);
        });

        Map.setOnClickListener(view -> {
            Intent intent1 = new Intent(getBaseContext(), SearchShelterActivity.class);
            intent1.putExtra("shelter_list", filteredList);
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Binary Serialization", "Data Saved");
        bs.saveBinary(file);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Binary Serialization", "Data Saved");
        bs.saveBinary(file);
    }
}
