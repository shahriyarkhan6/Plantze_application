package com.example.plantze_application.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.R;
import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements LoginView{

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    Button loginbutton;
    Button forgotpasswordbutton;
    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        forgotpasswordbutton = findViewById(R.id.forgot_password_button);
        forgotpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //User will be directed to a page where they can reset their password if they have forgotten it

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        presenter = new LoginPresenter(this);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginbutton = findViewById(R.id.login_button);

        loginbutton.setOnClickListener(v -> {
            String email;
            String password;
            email = String.valueOf(editTextEmail.getText()).trim();
            password = String.valueOf(editTextPassword.getText()).trim();

            //presenter will handle the login logic

            presenter.handleLoginCredentials(email, password);
        });
    }
    //Function to display any message to the user

    @Override
    public void displaymessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Function to navigate from login to Annual Footprint page for a new user

    @Override
    public void navigateToAnnualFootprintActivity(String message) {
        Intent intent = new Intent(this, AnnualFootprintActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
        finish();
    }

   //Function to navigate from login to the main homepage of the app for an old user

    @Override
    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}