package com.example.johnbeckner.buzzshelter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;

public class ForgotActivity extends AppCompatActivity {

    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            Log.e("", "starting to send an email...");
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@example.com", "BuzzShelter Password Retrieval Tool"));
            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Log.e("app", "Message created");
            Transport.send(msg);
            Log.e("app", "Message sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SendEmailTask extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Object... objects) {
            sendEmail((Session)objects[0], (String)objects[1], (String)objects[2], (String)objects[3]);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        TextView emailView = findViewById(R.id.emailView);
        EditText emailText = findViewById(R.id.emailField);

        Button goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(v -> {
            if (Auth.findUser(emailText.getText().toString()) == null) {
                Toast.makeText(this, "I can't seem to find that email.", Toast.LENGTH_SHORT).show();
                if (Auth.getUsers() == null)
                    Log.e("users", "NULLELRLRLRL");
                else
                    Log.e("users", Arrays.toString(Auth.getUsers().toArray()));
                return;
            }

            Random rand = new Random(System.currentTimeMillis());
            int randomNumber = rand.nextInt(999999 - 111110) + 111111;
            String pin = "" + randomNumber;
            Log.e("pin", pin);

            // I just made this gmail account today... feel free to login to it
            final String email = "cs2340tempemail@gmail.com";
            final String password = "CS2340TempPassword";

            // send email
            // SMTP HOST: smtp.gmail.com
            // SMTP PORT: 587
            // USE SSL: If available
            // SMTP Auth: Login
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };
            Session session = Session.getInstance(properties, authenticator);
            new SendEmailTask().execute(session, emailText.getText().toString(), "Your code", pin);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please enter the number you received in your email:");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("Go!", (d, w) -> {
                if (input.getText().toString().equals(pin)) {
                    Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();
                    emailView.setText("Your password is: ");
                    emailText.setText(Auth.findUser(emailText.getText().toString()).getPassword());
                } else {
                    Toast.makeText(this, "That's not right...", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> finish());
    }
}
