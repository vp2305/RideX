package com.example.ridex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameInputField, lastNameInputField, emailAddressInputField, passwordInputField;
    Button signUpBtn, signUpWithGoogleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the contentView
        setContentView(R.layout.activity_sign_up);
        // Getting a reference for the variables
        firstNameInputField = findViewById(R.id.firstName_inputField);
        lastNameInputField = findViewById(R.id.lastName_inputField);
        emailAddressInputField = findViewById(R.id.email_inputField);
        passwordInputField = findViewById(R.id.password_inputField);
        signUpBtn = findViewById(R.id.signUp_btn);
        signUpWithGoogleBtn = findViewById(R.id.signUp_with_google_btn);
        // End of the reference for all the variable.

    }

    public void signUpBtnListener(View view) {
        // Clicked on the sign up btn
        String firstName = firstNameInputField.getText().toString();
        String lastName = lastNameInputField.getText().toString();
        String email = emailAddressInputField.getText().toString();
        String password = passwordInputField.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()
                && !password.isEmpty()){
            // Provided all the information.
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Please make sure to provide all the information.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void signUpWithGoogleBtnListener(View view){
        // Clicked on the sign up with google btn
        Toast.makeText(getApplicationContext(), "Sign up with Google!",
                Toast.LENGTH_SHORT).show();
    }

    public void signInBtnTransition(View view){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}