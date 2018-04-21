package com.example.johnbeckner.buzzshelter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class MainAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView userList = (ListView) findViewById(R.id.List);
        File file = new File(this.getFilesDir(), "data.bin");
        BinarySerialize bs = new BinarySerialize();

        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                Auth.getUsers()){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (Auth.getUsers().get(position).isBanned()) {
                    view.setBackgroundColor(Color.RED);
                }
                return view;
            }
        };
        userList.setAdapter(arrayAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User pressed = (User) userList.getItemAtPosition(i);
                pressed.toggleBanned();
                if (pressed.isBanned()) {
                    userList.getChildAt(i).setBackgroundColor(Color.RED);
                } else {
                    userList.getChildAt(i).setBackgroundColor(Color.WHITE);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder logoutAlt = new AlertDialog.Builder(MainAdminActivity.this);
                logoutAlt.setTitle("Logout?");
                logoutAlt.setMessage("Do you want to log out?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bs.saveBinary(file);
                                startActivity(new Intent(MainAdminActivity.this, LoginActivity.class));
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
    }

}
