package com.example.johnbeckner.buzzshelter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

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
                    Editable nameText = name.getText();
                    Editable emailText = email.getText();
                    Editable passText = password.getText();
                    User newUser = new User(
                            nameText.toString(),
                            emailText.toString(),
                            passText.toString(),
                            (UserType) userTypeValue.getSelectedItem());
                    Auth.addUser(newUser);
                    if (Auth.authenticate(nameText.toString(),
                            passText.toString()) != null) {
                        // is new user is able to login
                        Context settingCon = getApplicationContext();
                        SharedPreferences settings = settingCon.getSharedPreferences("User", 0);
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putString("fullName", newUser.getName());
                        editor.apply();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                }
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LaunchActivity.class));
                finish();
            }
        });
    }

    private boolean validInputs() {

        Editable nameText = this.name.getText();
        Editable emailText = this.email.getText();
        Editable passText = this.password.getText();
        Object uTypeValSDelected = this.userTypeValue.getSelectedItem();

        String name = nameText.toString();
        String email = emailText.toString();
        String password = passText.toString();
        String type = ((UserType) uTypeValSDelected).toString();

        if (name.matches("")) {
            Toast newToast = Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT);
            newToast.show();
            return false;
        }else if (email.matches("")) {
            // we can also check if the email is a valid email here, but for now I'm just making sure it exists
            Toast newToast = Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT);
            newToast.show();
            return false;
        } else if (password.matches("")) {
            // same as email, we can enforce rules here, but for now I ust want it to exist
            Toast newToast = Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT);
            newToast.show();

            return false;
        } else if (type.matches("")) {
            Toast newToast = Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT);
            newToast.show();

            return false;
        }

        User test = Auth.findUser(new User(name, email, password));

        if (!(test.equals(new User()))) {
            Toast newToast = Toast.makeText(this, "user name already in use", Toast.LENGTH_SHORT);
            newToast.show();
            return false;
        }

        return true;
    }
}

