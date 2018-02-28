package com.example.johnbeckner.buzzshelter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ShelterInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        TextView name = (TextView) findViewById(R.id.ShelterName);
        TextView capacity = (TextView) findViewById(R.id.shelterCap);
        TextView address = (TextView) findViewById(R.id.shelterAddress);
        TextView restrictions = (TextView) findViewById(R.id.shelterRes);
        TextView phone = (TextView) findViewById(R.id.shelterPhone);

        String[] info = getIntent().getStringArrayExtra("shelter Info");

        /*
        info[0] = shelter name
        info[1] = capacity
        info[2] = Restrictions
        info[3] = longitude
        info[4] = latitude
        info[5] = address
        info[6] = phoneNumber
        info[7] = notes
         */
        name.setText(info[0]);
        capacity.setText(info[1]);
        address.setText(info[5]);
        restrictions.setText(info[2]);
        phone.setText(info[6]);
    }
}
