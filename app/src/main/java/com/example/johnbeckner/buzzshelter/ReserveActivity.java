package com.example.johnbeckner.buzzshelter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("OverlyLongMethod")
public class ReserveActivity extends AppCompatActivity {

    @SuppressWarnings("FeatureEnvy") // to fix this warning, we would need to re-write User
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        final Shelter info = getIntent().getParcelableExtra("shelter_info");
        Shelter shelterInList = ShelterList.findShelter(info);
        Button reserveButton = findViewById(R.id.confirmButton);
        Button cancelButton = findViewById(R.id.dismissButton);
        EditText numReserve = findViewById(R.id.numReserve);

        reserveButton.setOnClickListener(v -> {
            String input = numReserve.getText().toString();

            int count = Integer.parseInt(input);
            if (count > shelterInList.getCapacity()) {
                Toast.makeText(this, "Unfortunately, there are not that many available reservations",
                        Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                SharedPreferences settings = getApplicationContext().getSharedPreferences("User", 0);
                String user = settings.getString("fullName", "DEFAULT");
                User temp = new User();
                temp.setName(user);
                User userInList = Auth.findUser(temp);

                if (userInList != null ? userInList.getName().equals("DEFAULT") : false) {
                    Toast.makeText(this, "You're not logged in!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (userInList.isHasReservation()) {
                    Toast.makeText(this, "You already have a reservation", Toast.LENGTH_LONG).show();
                    return;
                }

                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", (di, i) -> {
                            shelterInList.reserve(user, Integer.parseInt(numReserve.getText().toString()));
                            userInList.setHasReservation(true);
                            Toast.makeText(this, "You have successfully reserved a spot.", Toast.LENGTH_LONG).show();

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", (Parcelable) shelterInList);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        })
                        .setNegativeButton("No", (di, i) -> {
                            Toast.makeText(this, "You have not reserved a spot.", Toast.LENGTH_LONG).show();
                        });

                builder.show();
            }
//            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });
    }
}
