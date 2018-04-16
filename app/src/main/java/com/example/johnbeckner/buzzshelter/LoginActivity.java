package com.example.johnbeckner.buzzshelter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

// --Commented out by Inspection START (4/15/2018 9:57 PM):
//    /**
//     * A dummy authentication store containing known user names and passwords.
//     */
//    private static final String[] DUMMY_CREDENTIALS = new String[]{
//            "foo@example.com:hello", "bar@example.com:world", "user:pass"
//    };
// --Commented out by Inspection STOP (4/15/2018 9:57 PM)
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());

        Button mCancelButton = findViewById(R.id.cancel_action);
        mCancelButton.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, LaunchActivity.class)));

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        Editable emailText = mEmailView.getText();
        String email = emailText.toString();
        Editable passText = mPasswordView.getText();
        String password = passText.toString();

        boolean cancel = false;
        View focusView = null;

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        Resources res = getResources();
        int shortAnimTime = res.getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        ViewPropertyAnimator animation = mLoginFormView.animate();
        animation = animation.setDuration(shortAnimTime);
        animation.alpha(show ? 0 : 1);
        animation.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        ViewPropertyAnimator animation1 = mProgressView.animate();
        animation1.alpha(show ? 1 : 0);
        animation1.setListener(new AnimatorListenerAdapter() {
             @Override
             public void onAnimationEnd(Animator animation) {
                 mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
             }
         });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    // I honestly have no idea how to fix this
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Collection<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        // --Commented out by Inspection (4/15/2018 9:57 PM):int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private User mUser;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            mUser = Auth.authenticate(mEmail, mPassword);
            return mUser != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);

            if (success) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Context con = getApplicationContext();
                SharedPreferences settings = con.getSharedPreferences("User", 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("fullName", mUser.getName());
                editor.apply();

                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }
}

