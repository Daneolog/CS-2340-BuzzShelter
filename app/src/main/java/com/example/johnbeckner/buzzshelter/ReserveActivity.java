package com.example.johnbeckner.buzzshelter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Reservation activity for User
 * @author team
 * @version 1.0
 */
@SuppressWarnings("OverlyLongMethod")
public class ReserveActivity extends AppCompatActivity {

    @SuppressWarnings("FeatureEnvy") // to fix this warning, we would need to re-write User
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        Intent intent = getIntent();
        final Shelter info = intent.getParcelableExtra("shelter_info");
        Shelter shelterInList = ShelterList.findShelter(info);
        Button reserveButton = findViewById(R.id.confirmButton);
        Button cancelButton = findViewById(R.id.dismissButton);
        EditText numReserve = findViewById(R.id.numReserve);

        reserveButton.setOnClickListener(v -> {
            Editable res = numReserve.getText();
            String input = res.toString();

            int count = Integer.parseInt(input);
            if (count > ((shelterInList != null) ? shelterInList.getCapacity() : 0)) {
                Toast toast = Toast.makeText(this,
                        "Unfortunately, there aren't that many available reservations" ,
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                Context con = getApplicationContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                SharedPreferences settings = con.
                        getSharedPreferences("User", 0);
                String user = settings.getString("fullName", "DEFAULT");
                User temp = new User();
                temp.setName(user);
                User userInList = Auth.findUser(temp);

                if ((userInList != null) && "DEFAULT".equals(userInList.getName())) {
                    Toast toast = Toast.makeText(this,"You're not logged in!" , Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if ((userInList != null) && userInList.isHasReservation()) {
                    Toast toast = Toast.makeText(this,"You already have a reservation" ,
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                Toast toast = Toast.makeText(this,"You have not reserved a spot." ,
                        Toast.LENGTH_LONG);

                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", (di, i) -> {
                    Editable resNum = numReserve.getText();
                    shelterInList.reserve(user,
                            Integer.parseInt(resNum.toString()));
                    userInList.setHasReservation(true);
                    Toast toast1 = Toast.makeText(this,"You have successfully reserved a spot." ,
                            Toast.LENGTH_LONG);
                    toast1.show();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", (Parcelable) shelterInList);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    });
                builder.setNegativeButton("No", (di, i) -> toast.show());

                builder.show();
            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });
    }
}
