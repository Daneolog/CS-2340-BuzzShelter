package com.example.johnbeckner.buzzshelter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReserveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        final Shelter info = getIntent().getParcelableExtra("shelter_info");
        Button reserveButton = findViewById(R.id.confirmButton);
        Button cancelButton = findViewById(R.id.dismissButton);
        EditText numReserve = findViewById(R.id.numReserve);

        reserveButton.setOnClickListener(v -> {
            String input = numReserve.getText().toString();
            if (input.equals("") || !input.matches("\\d")) {
                Toast.makeText(this, "Please enter a number.", Toast.LENGTH_LONG).show();
            } else {
                int count = Integer.parseInt(input);
                if (count > info.getCapacity()) {
                    Toast.makeText(this, "Unfortunately, there are not that many available reservations",
                        Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Are you sure?")
                            .setPositiveButton("Yes", (di, i) -> {
                                info.reserve(Integer.parseInt(numReserve.getText().toString()));
                                Toast.makeText(this, "You have successfully reserved a spot.", Toast.LENGTH_LONG).show();

                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", info);
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            })
                            .setNegativeButton("No", (di, i) -> {
                                Toast.makeText(this, "You have not reserved a spot.", Toast.LENGTH_LONG).show();
                            });

                    builder.show();
                }
            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });
    }
}
