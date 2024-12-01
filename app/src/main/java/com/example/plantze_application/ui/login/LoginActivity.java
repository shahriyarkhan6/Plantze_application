package com.example.plantze_application.ui.login;

import static android.app.ProgressDialog.show;

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
import androidx.fragment.app.Fragment;

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.R;
import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.dashboard.DashboardFragment;
import com.example.plantze_application.ui.dashboard.DashboardViewModel;
import com.example.plantze_application.ui.registration.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    Button loginbutton;
    Button forgotpasswordbutton;
    FirebaseAuth mAuth;
    FirebaseFirestore db;


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

        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

                //Check if email inputted is empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Check if password inputted is empty
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // Sign in
                                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //Store the user's id so then it can be accessed and used from anywhere in the app
                                    if (user != null){
                                        String userID = user.getUid(); // This UID is to be used in Firestore

                                        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("USER_ID", userID);
                                        editor.apply();

                                        // Fetch Login# from Firestore
                                        db.collection("users").document(userID).get()
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful() && task1.getResult() != null) {
                                                        DocumentSnapshot documentSnapshot = task1.getResult();
                                                        if (documentSnapshot.exists()) {
                                                            String loginNumber = documentSnapshot.getString("Login#");
                                                            if ("0".equals(loginNumber)) {

                                                                // New user: Update Login# to 1
                                                                Map<String, Object> update = new HashMap<>();
                                                                update.put("Login#", "1");
                                                                db.collection("users").document(userID).update(update)
                                                                        .addOnCompleteListener(updateTask -> {
                                                                            if (updateTask.isSuccessful()) {
                                                                                // Navigate to the AnnualFootPrintActivity for the new user
                                                                                Intent intent = new Intent(LoginActivity.this, AnnualFootprintActivity.class);
                                                                                intent.putExtra("message", "Let's get started! We will calculate your current carbon footprint based on your lifestyle. You only need to do this once, unless you choose to do so again.");
                                                                                startActivity(intent);
                                                                                finish();
                                                                            } else {
                                                                                Toast.makeText(LoginActivity.this, "Failed to update user status.", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            } else {
                                                                // Old user: Navigate to the main activity
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        } else {
                                                            Toast.makeText(LoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Failed to fetch user data.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }


                                } else {
                                    // If sign in fails, then display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Invalid email or password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



    }
}