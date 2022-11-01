package com.example.ridex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passInput;
    Button signInBtn, signInWithGoogleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the contentView
        setContentView(R.layout.activity_login);
        // Initialize the variables
        emailInput = findViewById(R.id.email_inputField);
        passInput = findViewById(R.id.password_inputField);
        signInBtn = findViewById(R.id.signIn_btn);
        signInWithGoogleBtn = findViewById(R.id.signIn_with_google_btn);
        // End of variable initialization
    }

    public void signInBtnListener(View view){
        // User clicked on the sign in btn
        String emailInputText = emailInput.getText().toString();
        String passwordInputText = passInput.getText().toString();
        if (!emailInputText.isEmpty() && !passwordInputText.isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Email or password cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInWithGoogleBtnListener(View view){
        // User clicked on the sign in with google btn
        Toast.makeText(getApplicationContext(), "Sign in with google is pressed!", Toast.LENGTH_SHORT).show();
    }

    public void signUpBtnTransition(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}