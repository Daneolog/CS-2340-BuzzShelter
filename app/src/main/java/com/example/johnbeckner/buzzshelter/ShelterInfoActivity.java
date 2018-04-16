package com.example.johnbeckner.buzzshelter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Shelter details activity
 * @author team
 * @version 1.0
 */
public class ShelterInfoActivity extends AppCompatActivity {
    private Shelter info;

    private TextView name;
    private TextView capacity;
    private TextView address;
    private TextView restrictions;
    private TextView phone;
    private Button reserveButton;
    private Button dropReserveButton;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        name = findViewById(R.id.shelterName);
        capacity = findViewById(R.id.shelterCap);
        address = findViewById(R.id.shelterAddress);
        restrictions = findViewById(R.id.shelterRes);
        phone = findViewById(R.id.shelterPhone);
        reserveButton = findViewById(R.id.reserveButton);
        dropReserveButton = findViewById(R.id.DropReservation);
        returnButton = findViewById(R.id.returnButton);

        Intent intent = getIntent();
        info = intent.getParcelableExtra("shelter_info");
        setShelterInfo();
    }

    // I don't think this is feature envy, we only call on Shelter twice in the method
    @SuppressWarnings("FeatureEnvy")
    private void setShelterInfo() {
        name.setText(info.getShelterName());
        Map<String, Integer> reservation = info.getReservations();
        Set<String> keys = reservation.keySet();
        capacity.setText(String.format("%d\nThis has been reserved by %s",
                info.getCapacity(), keys.toString()));
        address.setText(info.getAddress());
        restrictions.setText(info.getRestrictions());
        phone.setText(info.getPhoneNumber());

        Context appCon = getApplicationContext();
        SharedPreferences settings = appCon.getSharedPreferences("User", 0);
        String user = settings.getString("fullName", "DEFAULT");

        dropReserveButton.setOnClickListener(v -> {
            if (info.dropReservation(user)) {
                User temp = new User();
                temp.setName(user);
                User findUsr = Auth.findUser(temp);
                User nonNull = Objects.requireNonNull(findUsr);
                nonNull.setHasReservation(false);
                Toast toast = Toast.makeText(this, "Successfully dropped reservation",
                        Toast.LENGTH_LONG);
                toast.show();
                recreate();
            } else {
                Toast toast = Toast.makeText(this, "You don't have a reservation at this shelter.",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });

        reserveButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReserveActivity.class);
            intent.putExtra("shelter_info", (Parcelable) info);
            startActivityForResult(intent, 1);
        });

        returnButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                info = data.getParcelableExtra("result");
                setShelterInfo();
            }
        }
    }
}
