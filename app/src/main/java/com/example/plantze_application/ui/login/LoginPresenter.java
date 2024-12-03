package com.example.plantze_application.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter {

    public final LoginView view;
    public final FirebaseAuth mAuth;
    public final FirebaseFirestore db;

    public LoginPresenter(LoginView view){

        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

    }


    public void handleLoginCredentials(String email, String password) {

        //Check if email inputted is empty

        if (TextUtils.isEmpty(email)) {
            view.displaymessage("Enter Email");
            return;
        }

        //check if password inputted is empty

        if (TextUtils.isEmpty(password)) {
            view.displaymessage("Enter Password");
            return;
        }

        //Firebase Authentication now handles login credentials and its verification

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        //Sign in was successful

                        view.displaymessage("Login Successful");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userID = user.getUid();

                            //Save the current user ID

                            saveCurrentUserInPreferences(userID);

                            //Call function to check if current user is new or old upon this login and direct them to the appropriate page

                            getUserStatus(userID);
                        }
                    } else {

                        //user has inputted wrong email, password, or both

                        view.displaymessage("Invalid email or password.");
                    }
                });
    }

    //This will save the current user's userID so that it can be accessed from anywhere in the app, which will allow any section of the app to access the user's information in the Firestore and modify it

    private void saveCurrentUserInPreferences(String userID) {
        Context context = (Context) view;
        SharedPreferences sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("USER_ID", userID);
        editor.apply();
    }

   //This will check the user to see if they are new or old upon logging in

    private void getUserStatus(String userID) {
        db.collection("users").document(userID).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {

                            //Store the current user's Login# so then it can be used to determine whether the user is new or old

                            String loginnum = documentSnapshot.getString("Login#");

                            //If user is new, then update for next time. Regardless of whether the user is new or old, this will direct them to the correct page depending on their status

                            if ("0".equals(loginnum)) {

                                //The user is new, so we call the appropriate function

                                updateLoginnum(userID);

                            } else {

                                //The current user is old, so they go to the main home page of the app

                                view.navigateToMainActivity();
                            }
                        } else {
                            view.displaymessage("User data not found.");
                        }
                    } else {
                        view.displaymessage("Failed to retrieve user data.");
                    }
                });
    }

    private void updateLoginnum(String userID) {

        //This will update the new current user to old for proper navigation after login next time, and will now direct the user to the page that calculates their annual footprint

        Map<String, Object> update = new HashMap<>();

        //update the login# to convert current user to old user for next login

        update.put("Login#", "1");
        db.collection("users").document(userID).update(update)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        //Go to the Annual Footprint Activity for the first time with a brief message as a new user

                        view.navigateToAnnualFootprintActivity("Let's get started! We will calculate your current carbon footprint based on your lifestyle. You only need to do this once, unless you choose to do so again.");

                    } else {
                        view.displaymessage("Failed to update user status.");
                    }
                });
    }

}
