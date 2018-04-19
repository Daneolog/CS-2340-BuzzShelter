package com.example.johnbeckner.buzzshelter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        TextView emailText = findViewById(R.id.emailField);

        Button goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(v -> {
            Random rand = new Random(System.currentTimeMillis());
            int randomNumber = rand.nextInt(999999 - 111110) + 111111;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please enter the number you received in your email:");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

            builder.setPositiveButton("Go!", (d, w) -> {
                emailText.setText(randomNumber);
            });

            builder.show();
        });
    }
}
