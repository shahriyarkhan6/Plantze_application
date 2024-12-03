package com.example.plantze_application.ui.dashboard;

import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardFirebase {
    final FirebaseFirestore database;
    ArrayList<Entry> entries;
    String userid;
    public DashboardFirebase(){
        //creating firebase
        database = FirebaseFirestore.getInstance();
        entries = new ArrayList<>();
        //find current user id
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            userid = mAuth.getCurrentUser().getUid();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillEmptyEntries(ArrayList<Entry> entries, int numEntries){
        entries.sort(new EntryXComparator());
        for(int i = 0; i < numEntries; i++) {
            if(!containsDay(entries, i + 1)){
                entries.add(new Entry(i + 1, 0));
            }
        }
    }
    private boolean containsDay(ArrayList<Entry> entries, int x){
        for(Entry day: entries){
            if(day.getX() == x)
                return true;
        }
        return false;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getC02ForDays(LocalDate date, TextView view, int numDays,
                              ChronoUnit timescale, GetUserDataCallback i){

        //first query all activity data from current user
        DocumentReference r = database.collection("users").document(userid);

        LocalDate lowerBound = date.minusDays(numDays);
        //then, query all activities at date
        //ValueEventListener retrieves all data at location upon change/ initial query in DataSnapshot
        r.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    Log.d("DashboardFirebase", "successfully found at:" + date);
                    ArrayList<Entry> entries = new ArrayList<>(numDays);
                    int index = 0;
                    float totalEmission = 0;
                    boolean added = false;
                    float activityEmission = 0;
                    DocumentSnapshot userData = task.getResult();
                    if(!userData.exists()){
                        Log.d("DashboardFirebase", "user info empty" );
                        return;
                    }
                    List<Map<String, Object>> activities = (List<Map<String, Object>>) userData.get("Activities");
                    if(activities != null && !activities.isEmpty()){
                        //iterates through each activity
                        for (Map<String, Object> document : activities) {
                            Log.d("DashboardFirebase", "Accessed Activity");

                            //find only the activities with the date we want
                            LocalDate activityDate = LocalDate.parse((String)document.get("Date"));
                            if(activityDate.isAfter(lowerBound) && activityDate.isBefore(date.plusDays(1))){
                                index = numDays - (int)activityDate.until(date, timescale);
                                if(document.get("Emission") instanceof Double){
                                    activityEmission = ((Double) document.get("Emission")).floatValue();
                                }
                                if(document.get("Emission") instanceof Long){
                                    activityEmission = ((Long)document.get("Emission")).floatValue();
                                }
                                //to replace with func
                                for(Entry i: entries){
                                    if(i.getX() == index) {
                                        i.setY(i.getY() + activityEmission);
                                        added = true;
                                    }
                                }
                                if(!added) {
                                    entries.add(new Entry(index, activityEmission));
                                }
                                added = false;
                                totalEmission += activityEmission;
                                Log.d("DashboardFirebase", "Emissions:"+ activityEmission);
                                activityEmission = 0;
                            }
                        }
                    }
                    fillEmptyEntries(entries, numDays);
                    view.setText("Co2 Emission for the past" + numDays + "days: " + totalEmission + " kgs");
                    i.onCallback(entries);
                }
                else{
                    Log.d("DashboardFirebase", "error retrieving user data");
                }
            }

        });

    }



    /*
    //requires android 8.0 to get date
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Entry> collectMonth(){
        LocalDate currentDate = LocalDate.now();
        int month_len = currentDate.lengthOfMonth();
        ArrayList<Entry> month = new ArrayList<>(month_len);
        //Sum up CO2 per day then put into Entry list with date as X and CO2 as Y
        for(int i = month_len; i > 0; i--){
            month.add( new Entry(i, this.getC02ForDay(currentDate.toString())));
            currentDate.minusDays(1);
        }
        return month;
    }
    //requires android 8.0 to get date
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Entry> collectYear(){

        LocalDate currentDate = LocalDate.now();
        int year_len = currentDate.lengthOfYear();
        ArrayList<Entry> year = new ArrayList<>(year_len);
        //Sum up CO2 per day then put into Entry list with date as X and CO2 as Y
        for(int i = year_len; i > 0; i--){
            year.add( new Entry(i, this.getC02ForDay(currentDate.toString())));
            currentDate.minusDays(1);
        }
        return year;
    }
    */

}
