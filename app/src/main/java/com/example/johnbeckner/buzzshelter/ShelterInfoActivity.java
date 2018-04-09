package com.example.johnbeckner.buzzshelter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class ShelterInfoActivity extends AppCompatActivity {
    Shelter info;

    TextView name;
    TextView capacity;
    TextView address;
    TextView restrictions;
    TextView phone;
    Button reserveButton;
    Button dropReserveButton;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        name = (TextView) findViewById(R.id.shelterName);
        capacity = (TextView) findViewById(R.id.shelterCap);
        address = (TextView) findViewById(R.id.shelterAddress);
        restrictions = (TextView) findViewById(R.id.shelterRes);
        phone = (TextView) findViewById(R.id.shelterPhone);
        reserveButton = findViewById(R.id.reserveButton);
        dropReserveButton = (Button) findViewById(R.id.DropReservation);
        returnButton = findViewById(R.id.returnButton);

        info = getIntent().getParcelableExtra("shelter_info");
        setShelterInfo();
    }

    @SuppressWarnings("FeatureEnvy") // I don't think this is feature envy, we only call on Shelter twice in the method
    private void setShelterInfo() {
        name.setText(info.getShelterName());
        capacity.setText(String.format("%d\nThis has been reserved by %s",
                info.getCapacity(), info.getReservations().keySet().toString()));
        address.setText(info.getAddress());
        restrictions.setText(info.getRestrictions());
        phone.setText(info.getPhoneNumber());

        SharedPreferences settings = getApplicationContext().getSharedPreferences("User", 0);
        String user = settings.getString("fullName", "DEFAULT");

        dropReserveButton.setOnClickListener(v -> {
            if (info.dropReservation(user)) {
                User temp = new User();
                temp.setName(user);
                Objects.requireNonNull(Auth.findUser(temp)).setHasReservation(false);
                Toast.makeText(this, "Successfully dropped reservation", Toast.LENGTH_LONG).show();
                recreate();
            } else {
                Toast.makeText(this, "You don't have a reservation at this shelter.", Toast.LENGTH_LONG).show();
            }
        });

        reserveButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReserveActivity.class);
            intent.putExtra("shelter_info", (Parcelable) info);
            startActivityForResult(intent, 1);
        });

        returnButton.setOnClickListener(v -> {
            finish();
        });
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
