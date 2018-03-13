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
//        Button cancelButton = findViewById(R.id.confirmButton);
        EditText numReserve = findViewById(R.id.numReserve);

        reserveButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure?")
                   .setPositiveButton("Yes", (di, i) -> {
                       info.reserve(Integer.parseInt(numReserve.getText().toString()));
                       Toast.makeText(this, "You have successfully reserved a spot.", Toast.LENGTH_SHORT).show();

                       Intent returnIntent = new Intent();
                       returnIntent.putExtra("result", info);
                       setResult(Activity.RESULT_OK, returnIntent);
                       finish();
                   })
                   .setNegativeButton("No", (di, i) -> {
                       Toast.makeText(this, "You have not reserved a spot.", Toast.LENGTH_SHORT).show();
                   });

            builder.show();
        });

//        cancelButton.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//            builder.setMessage("Are you sure?")
//                    .setPositiveButton("Yes", (di, i) -> {
//                        info.reserve(Integer.parseInt(numReserve.getText().toString()));
//                        Toast.makeText(this, "You have successfully reserved a spot.", Toast.LENGTH_SHORT);
//
//                        Intent returnIntent = new Intent();
//                        returnIntent.putExtra("result", info);
//                        setResult(Activity.RESULT_OK, returnIntent);
//                        finish();
//                    })
//                    .setNegativeButton("No", (di, i) -> {
//                        Toast.makeText(this, "You have not reserved a spot.", Toast.LENGTH_SHORT);
//                    });
//
//            builder.show();
//        });
    }
}
