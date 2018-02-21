package com.example.johnbeckner.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private Spinner userTypeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.name_text_field);
        email = (EditText) findViewById(R.id.email_text_field);
        password = (EditText) findViewById(R.id.password_text_field);
        userTypeValue = (Spinner) findViewById(R.id.RegSpinner);

        userTypeValue = (Spinner) findViewById(R.id.RegSpinner);
        userTypeValue.setAdapter(new ArrayAdapter<UserType>(this,
                android.R.layout.simple_spinner_item, UserType.values()));

        Button mRegisterUser = (Button) findViewById(R.id.registerButton2);
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInputs()) {
                    User newUser = new User(
                            name.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            (UserType) userTypeValue.getSelectedItem());
                    Auth.addUser(newUser);
                    if (Auth.authenticate(name.getText().toString(),
                            password.getText().toString())) {
                        // is new user is able to login
                        startActivity(new Intent(Registration.this, MainActivity.class));

                    }
                }
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, LaunchActivity.class));
                finish();
            }
        });
    }

    private boolean validInputs() {
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String type = this.userTypeValue.getSelectedItem().toString();

        if (name.matches("")) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        }else if (email.matches("")) {
            // we can also check if the email is a valid email here, but for now I'm just making sure it exists
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.matches("")) {
            // same as email, we can enforce rules here, but for now I ust want it to exist
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (type.matches("")) {
            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

