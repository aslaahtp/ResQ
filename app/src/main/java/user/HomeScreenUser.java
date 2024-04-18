/*
to show the home screen to the users.
 */


package user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.databinding.ActivityHomeScreenUserBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authentication.SignIn;
import commonClasses.AboutUs;
import commonClasses.EventsActivity;
import commonClasses.EventsAdapter;
import commonClasses.EventsModel;
import commonClasses.LiveChat;
import commonClasses.MainActivity;
import navigationBars.DrawerBaseActivity;
import volunteer.HomeScreenVolunteer;

public class HomeScreenUser extends DrawerBaseActivity implements View.OnClickListener {

    public static int backPressedCnt = 0;
    //declaring variables
    CardView safetyTipsCardView;
    CardView forecastCardView;
    CardView emergencyContactsCardView;
    CardView yourAreaCArdView;
    CardView requestHelpCardView;
    CardView cv_tutorials;
    CardView cv_add;
    BottomNavigationItemView home;
    BottomNavigationItemView helpline;
    BottomNavigationItemView aboutUs;
    List<EventsModel> packlist;
    EventsAdapter framesAdapter;
    RecyclerView recyclerView;
    SwitchCompat switchCompat;
    ActivityHomeScreenUserBinding activityHomeScreenUserBinding;

    public static void makeBackPressedCntZero() {
        backPressedCnt = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();

        DrawerLayout drawer = findViewById(R.id.drawerLayoutID);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //drawer.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeScreenUserBinding = ActivityHomeScreenUserBinding.inflate(getLayoutInflater());
        setContentView(activityHomeScreenUserBinding.getRoot());
        allocateActivityTitle("Home");

        //initializing the viewID
        initializeViewIDs();
        packlist = new ArrayList<EventsModel>();
        recyclerView = findViewById(R.id.recyclerView);
        addPacks();
        // working with the switches. by clicking on it the user will go the home page of volunteers
        // (if he is already registered)
        switchCompat = findViewById(R.id.switchID);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCompat.setChecked(false);
                checkVolunteerList(); // checking for volunteer list. if he is a volunteer he will get access.
            }
        });

        cv_tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenUser.this,MainActivity.class));
            }
        });


        //button click
        forecastCardView.setOnClickListener(this);
       cv_add.setOnClickListener(this);



        helpline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    private void initializeViewIDs() {
       cv_add= findViewById(R.id.cv_add);
        forecastCardView = findViewById(R.id.forecastCardViewID);
        cv_tutorials= findViewById(R.id.cv_tutorials);



        //home=findViewById(R.id.homeMenuID);
        helpline = findViewById(R.id.liveChatMenuID);
        aboutUs = findViewById(R.id.aboutUsMenuID);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.forecastCardViewID) {
            start_forecast_activity();
        } else if (v.getId() == R.id.cv_add) {
           start_addactivity();
        }  else if (v.getId() == R.id.liveChatMenuID) {
            start_LiveChat_activity();
        } else if (v.getId() == R.id.cv_tutorials) {
            start_AboutUs_activity();
        }

    }

    // checking for double click. if double click then exit.
    @Override
    public void onBackPressed() {
        backPressedCnt++;
        if (backPressedCnt == 1) {
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            startActivity(intent);
        }
    }


    /*
    checking for volunteer. if volunteer the go the volunteer page. otherwise show alert dialog to register.
     */
    private void checkVolunteerList() {

        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Volunteer List").document(uid);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    if (error != null) {
                        return;
                    }
                    if (value.exists()) {
                        String status = value.getString("Registered");
                        if (status.equals("yes")) {
                            switchCompat.setChecked(false);
                            start_HomeScreenVolunteer_activity(); // going to volunteer page
                        } else {
                            showDialog(); // to register as volunteer
                        }
                    } else {
                        showDialog(); // to register as volunteer
                    }
                }
            });
        } catch (Exception e) {
            showDialog();
        }
    }


    // showing the dialog to register as volunteer. if accepts then write on volunteer list and then go
    // to the volunteer page otherwise do nothing
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Not a volunteer");
        builder.setMessage("Confirm as a volunteer and take the responsibilities to the Humanity.");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                writeOnVolunteerList(); // writing on volunteer list
                Toast.makeText(getApplicationContext(), "Welcome to the world of Heroes.", Toast.LENGTH_SHORT).show();
                switchCompat.setChecked(false);
                start_HomeScreenVolunteer_activity(); // going to volunteer page
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switchCompat.setChecked(false);
                // nothing to do
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    to write on volunteer list if he accepts.
     */
    private void writeOnVolunteerList() {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Volunteer List").document(uid);


            Map<String, Object> info = new HashMap<>();

            info.put("Registered", "yes");
            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // nothing to do
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // nothing to do
                }
            });
        } catch (Exception e) {
            //
        }
    }

    private void start_HomeScreenVolunteer_activity() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenVolunteer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_SafetyTips_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, SafetyTips.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_forecast_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, Forecast.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_addactivity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void start_YourArea_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, YourArea.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_RequestHelp_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, RequestHelp.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_EmergencyRescueSOS_activity() {
        makeBackPressedCntZero();
        Intent intent = new Intent(this, EmergencyRescueSOS.class);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_AboutUs_activity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void start_LiveChat_activity() {
        Intent intent = new Intent(this, LiveChat.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void start_YourRequests_activity() {
        Intent intent = new Intent(this, YourRequests.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void addPacks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("events");
        // Toast.makeText(this, "ethi", Toast.LENGTH_SHORT).show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                packlist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    EventsModel posts = postSnapshot.getValue( EventsModel.class);
                    packlist.add(posts);
                    //  Toast.makeText(FramesActivity.this, "" + packlist.size(), Toast.LENGTH_SHORT).show();
                }


              EventsAdapter eventsAdapter = new EventsAdapter(packlist, HomeScreenUser.this);
               eventsAdapter.setSelectedItemPosition(1);

                recyclerView.setAdapter(eventsAdapter); // Set the adapter here
               eventsAdapter.notifyDataSetChanged(); // Call notifyDataSetChanged after setting the adapter
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(FramesActivity.this, "canc"+error, Toast.LENGTH_SHORT).show();
            }

        });
    }
}