package com.example.plantze_application.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.R;
import com.example.plantze_application.ui.dashboard.DashboardFragment;
import com.example.plantze_application.ui.dashboard.DashboardViewModel;
import com.example.plantze_application.ui.registration.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    Button loginbutton;
    Button forgotpasswordbutton;
    FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if(currentUser != null){

            //MIGHT NEED TO CHANGE TO DASHBOARD FRAGMENT

            //Intent intent = new Intent(LoginActivity.this, DashboardFragment.class);
            //startActivity(intent);
            //finish();
        //}
    }


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
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth=FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginbutton = findViewById(R.id.login_button);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email;
                String password;
                email = String.valueOf(editTextEmail.getText()).trim();
                password = String.valueOf(editTextPassword.getText()).trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                //call function here
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null){
                                        String userID = user.getUid(); // This UID is to be used in Firestore

                                        //This allows the userID to be shared from anywhere within the app

                                        //SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                                        //SharedPreferences.Editor editor = sharedPref.edit();
                                        //editor.putString("USER_ID", userID);
                                        //editor.apply();

                                        //This is the code you use to retrieve the current user's userID after login from anywhere:
                                        //SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                                        //String userID = sharedPref.getString("USER_ID", null);  // Default is null if not found

                                        //This is how you can modify user details after getting the user's userID:
                                        //Map<String, Object> updatedData = new HashMap<>();
                                        //updatedData.put("address", "123 Main Street");  // Example of adding new info
                                        //db.collection("users").document(userID)
                                                //.update(updatedData);


                                    }



                                    //MIGHT NEED TO CHANGE TO DASHBOARD FRAGMENT

                                    //Intent intent = new Intent(LoginActivity.this, DashboardViewModel.class);
                                    //startActivity(intent);
                                    //finish();

                                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    //startActivity(intent);
                                    //finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(LoginActivity.this, "Invalid email or password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



    }
}