package com.example.plantze_application.ui.dashboard;

import android.app.PendingIntent;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sumEntries(ArrayList<Entry> entries, int numEntries, ChronoUnit timescale){
        entries.sort(new EntryXComparator());
        float totalEmissions = 0;
        int location = 0;
        if(timescale == ChronoUnit.DAYS) {
            for (int i = 1; i <= numEntries; i++) {
                location = containsDay(entries, i);
                if(location == -1) {
                    entries.add(new Entry(i, totalEmissions));
                    Log.d("DashboardFirebase", "entry created at: " + i);
                } else {
                    Entry entry = entries.get(location);
                    totalEmissions += entry.getY();
                    entry.setY(totalEmissions);
                    Log.d("DashboardFirebase", "entry found at: " + i);
                }
            }
        }
        else if(timescale == ChronoUnit.MONTHS) {
            for (int i = 1; i <= 12; i++) {
                location = containsDay(entries, i);
                if(location == -1) {
                    entries.add(new Entry(i, totalEmissions));
                    Log.d("DashboardFirebase", "entry created at: " + i);
                } else {
                    Entry entry = entries.get(location);
                    totalEmissions += entry.getY();
                    entry.setY(totalEmissions);

                }
            }
        }

    }
    //returns index of entry, returns -1 if not found
    private int containsDay(ArrayList<Entry> entries, int x){
        int i = 0;
        for(Entry day: entries){
            if(day.getX() == x){
                Log.d("DashboardFirebase", "entry found at: " + i + " value: " +
                        day.getY());
                return i;
                }
            i++;
        }
        return -1;
    }
    private int containsType(ArrayList<PieEntry> entries, String type){
        int i = 0;
        for(PieEntry category: entries){
            if(category.getLabel().equals(type) ){
                Log.d("DashboardFirebase", "entry found at: " + i);
                return i;
            }
            i++;
        }
        return -1;
    }

    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getC02ForDays(LocalDate date, int numDays,
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
                    ArrayList<PieEntry> typeEntries = new ArrayList<>(4);
                    int index = 0;
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

                            //find only the activities within the range of dates we want
                            LocalDate activityDate = LocalDate.parse((String)document.get("Date"));
                            if(activityDate.isAfter(lowerBound) && activityDate.isBefore(date.plusDays(1))){
                                if(timescale == ChronoUnit.DAYS) {
                                    index = numDays - (int) activityDate.until(date, timescale);
                                }
                                if(timescale == ChronoUnit.MONTHS){
                                    index = 12 - (int) activityDate.until(date, timescale);
                                }
                                if(document.get("Emission") instanceof Double){
                                    activityEmission = ((Double) document.get("Emission")).floatValue();
                                }
                                if(document.get("Emission") instanceof Long){
                                    activityEmission = ((Long)document.get("Emission")).floatValue();
                                }

                                int location = containsDay(entries, index);
                                if(location != -1) {
                                    Entry entry = entries.get(location);
                                    entry.setY(entry.getY() + activityEmission);
                                }
                                else {
                                    Log.d("DashboardFirebase","added entry:" + index + " totalEmission");
                                    entries.add(new Entry(index, activityEmission));
                                }
                                Log.d("DashboardFirebase", "Emissions:"+ activityEmission);
                                // generate piechart entries
                                String category = (String)document.get("Category");
                                int typeLocation = containsType(typeEntries, category);
                                if(typeLocation != -1) {
                                    PieEntry slice = typeEntries.get(typeLocation);
                                    slice.setY(slice.getY() + activityEmission);
                                }
                                else {
                                    Log.d("DashboardFirebase","added entry:" + category);
                                    typeEntries.add(new PieEntry(activityEmission, category));
                                }
                                activityEmission = 0;
                            }
                        }
                    }
                    sumEntries(entries, numDays, timescale);
                    i.onCallback(entries, typeEntries);
                }
                else{
                    Log.d("DashboardFirebase", "error retrieving user data");
                }

            }

        });

    }
    private void getEmissionTypeData(){

    }



}
