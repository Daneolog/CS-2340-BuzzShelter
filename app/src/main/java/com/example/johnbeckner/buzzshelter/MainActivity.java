package com.example.johnbeckner.buzzshelter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView ShelterLV;
    private Button Filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ShelterLV = (ListView) findViewById(R.id.List);
        Filter = (Button) findViewById(R.id.FilterButton);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder logoutAlt = new AlertDialog.Builder(MainActivity.this);
                logoutAlt.setTitle("Logout?");
                logoutAlt.setMessage("Do you want to log out?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dismiss the alert
                            }
                        });
                AlertDialog alert = logoutAlt.create();
                alert.show();
            }
        });
        
        ArrayList<Shelter> filteredList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("Filtered Shelter List")) {
            filteredList = extras.getParcelableArrayList("Filtered Shelter List");
        } else {
            ShelterList.parseDatabase( this.getResources().openRawResource(R.raw.shelterdatabase));
            filteredList = ShelterList.getShelters();
        }

        ArrayAdapter<Shelter> arrayAdapter = new ArrayAdapter<Shelter>(
                this,
                android.R.layout.simple_list_item_1,
                filteredList
        );
        ShelterLV.setAdapter(arrayAdapter);

        ShelterLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter pressed = (Shelter) ShelterLV.getItemAtPosition(i);
                Intent intent = new Intent(getBaseContext(), ShelterInfoActivity.class);
                intent.putExtra("shelter Info", pressed.getInfo());
                startActivity(intent);
            }
        });

        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchShelterActivity.class));
            }
        });
    }

}
