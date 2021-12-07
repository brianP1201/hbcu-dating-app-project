package com.example.linkdatingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    private EditText etCreateUsername, etCreatePassword, etConfirmPassword, etAge, etDescription;
    private Spinner spinnerColleges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etCreateUsername = findViewById(R.id.etCreateUsername);
        etCreatePassword = findViewById(R.id.etCreatePassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnConfirmSignUp = findViewById(R.id.btnConfirmSignUp);
        etAge = findViewById(R.id.etAge);
        spinnerColleges = (Spinner) findViewById(R.id.spinnerColleges);
        etDescription = findViewById(R.id.etDescription);

        // Create ArrayAdapter using String array and Spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colleges_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColleges.setAdapter(adapter);

        etAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    etAge.setHint("How old are you?");
                } else {
                    etAge.setHint("");
                }
            }
        });


        btnConfirmSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etCreatePassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    etConfirmPassword.setError("Passwords do not match.");
                }
                else{
                    String username = etCreateUsername.getText().toString();
                    String password = etConfirmPassword.getText().toString();
                    String college = spinnerColleges.getSelectedItem().toString();
                    int age = Integer.parseInt(etAge.getText().toString());
                    String description = etDescription.getText().toString();
                    parseNewUser(username, password, college, age, description);
                }
            }
        });


    }

    private void parseNewUser(String username, String password, String college, int age, String description) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Set custom properties
        user.put("college", college);
        user.put("age", age);
        user.put("description", description);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i(TAG, "User Signup Successful");
                    goMainActivity();
                    Toast.makeText(SignUpActivity.this, "New account created successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "User signup error", e);
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
    }
}