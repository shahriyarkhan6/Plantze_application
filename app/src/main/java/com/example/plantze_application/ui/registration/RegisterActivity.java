package com.example.plantze_application.ui.registration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantze_application.R;
import com.example.plantze_application.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    TextInputEditText editTextConfirmPassword;
    TextInputEditText editTextFirstName;
    TextInputEditText editTextLastname;
    Button registerbutton;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private boolean isValidEmail(String email){
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        registerbutton = findViewById(R.id.register_button);
        editTextConfirmPassword = findViewById(R.id.confirm_password);
        editTextFirstName = findViewById(R.id.first_name);
        editTextLastname = findViewById(R.id.last_name);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                String password;
                String confirm_password;
                String first_name;
                String last_name;
                email = String.valueOf(editTextEmail.getText()).trim();
                password = String.valueOf(editTextPassword.getText()).trim();
                confirm_password = String.valueOf(editTextConfirmPassword.getText()).trim();
                first_name = String.valueOf(editTextFirstName.getText()).trim();
                last_name = String.valueOf(editTextLastname.getText()).trim();

                //Check if email inputted is empty

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if password inputted is empty

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if both passwords match

                if(!(password.equals(confirm_password))){
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if first name inputted is empty

                if(TextUtils.isEmpty(first_name)){
                    Toast.makeText(RegisterActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if last name inputted is empty

                if(TextUtils.isEmpty(last_name)){
                    Toast.makeText(RegisterActivity.this, "Enter last name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if both first name and last name inputted is empty

                if((TextUtils.isEmpty(first_name)) && (TextUtils.isEmpty(last_name))){
                    Toast.makeText(RegisterActivity.this, "Enter full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if email inputted is valid

                if(!(isValidEmail(email))){
                    Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Store the hashed password in database for security purposes

                String hashedpassword = BCrypt.hashpw(password, BCrypt.gensalt());

                //Firebase will now handle user creation.

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();

                                    // Get the currently signed-in user

                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    if (currentUser != null) {

                                        // Prepare user data

                                        String uid = currentUser.getUid();
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("First Name", first_name);
                                        user.put("Last Name", last_name);
                                        user.put("Email", email);
                                        user.put("Password", hashedpassword);
                                        user.put("User ID", uid);
                                        user.put("Login#","0");

                                        // Save user data to Firestore using the UID as the document ID

                                        db.collection("users").document(uid)
                                                .set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        // Navigate to LoginActivity

                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        // Show error if Firestore write fails

                                                        Log.e("Firestore", "Error adding user to Firestore", e);
                                                        Toast.makeText(RegisterActivity.this, "Failed to save user data. Please try again.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }

                                } else{

                                    // If sign in fails, then display a message to the user.

                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                            }}



                        });
            }
        });

    }
}