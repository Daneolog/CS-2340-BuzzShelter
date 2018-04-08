package com.example.johnbeckner.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.File;

/**
 * Starting screen activity
 * @author team
 * @version 1.0
 */
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button loginButton = findViewById(R.id.loginScreen);
        Button registerButton = findViewById(R.id.registerScreen);

        File file = new File(this.getFilesDir(), "data.bin");
        if (file.exists()) {
            BinarySerialize bs = new BinarySerialize();
            bs.loadBinary(file);
        }

        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });
    }
}
