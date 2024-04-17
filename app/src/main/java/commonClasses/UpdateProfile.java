
package commonClasses;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.MainActivity;
import com.example.android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.HashMap;
import java.util.Map;

import user.HomeScreenUser;

public class UpdateProfile extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private final String KEY_EMAIL = "Email";
    private final String KEY_NAME = "Name";
    private final String KEY_CONTACT1 = "Contact1";
    private final String KEY_CONTACT2 = "Contact2";
    private final String KEY_GENDER = "Gender";
    private final String KEY_DISTRICT = "District";
    private final String KEY_THANA = "Thana";
    private final String KEY_AREA = "Area";
    private final String KEY_ROAD = "Road";
    private final String KEY_HOUSE = "House";

    EditText nameEditText;
    EditText emailEditText;
    EditText contact1EditText;
    EditText contact2EditText;
    EditText genderEditText;
    EditText districtEditText;
    EditText thanaEditText;
    EditText areaEditText;
    EditText roadEditText;
    EditText houseEditText;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Connect views with ids
        connectWithIDs();


        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles");

        // Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        databaseReference = databaseReference.child(uid);
    }

    private void connectWithIDs() {
        nameEditText = findViewById(R.id.nameID);
        emailEditText = findViewById(R.id.emailID);
        contact1EditText = findViewById(R.id.contact1ID);
        contact2EditText = findViewById(R.id.contact2ID);
        genderEditText = findViewById(R.id.genderID);
        districtEditText = findViewById(R.id.districtID);
        thanaEditText = findViewById(R.id.thanaID);
        areaEditText = findViewById(R.id.areaID);
        roadEditText = findViewById(R.id.roadID);
        houseEditText = findViewById(R.id.houseID);
    }

    public void updateProfile(View v) {
        // Getting the info's from the editTexts.
        Toast.makeText(this, "ethi", Toast.LENGTH_SHORT).show();
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String contact1 = contact1EditText.getText().toString().trim();
        String contact2 = contact2EditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String district = districtEditText.getText().toString().trim();
        String thana = thanaEditText.getText().toString().trim();
        String area = areaEditText.getText().toString().trim();
        String road = roadEditText.getText().toString().trim();
        String house = houseEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name can not be empty!");
            nameEditText.requestFocus();
            return;
        }
        if (contact1.isEmpty()) {
            contact1EditText.setError("Contact can not be empty!");
            contact1EditText.requestFocus();
            return;
        }
        if (district.isEmpty()) {
            districtEditText.setError("District can not be empty!");
            districtEditText.requestFocus();
            return;
        }

        // Create a Map to store user info
        Map<String, Object> info = new HashMap<>();
        info.put(KEY_NAME, name);
        info.put(KEY_EMAIL, email);
        info.put(KEY_CONTACT1, contact1);
        info.put(KEY_CONTACT2, contact2);
        info.put(KEY_GENDER, gender);
        info.put(KEY_DISTRICT, district);
        info.put(KEY_THANA, thana);
        info.put(KEY_AREA, area);
        info.put(KEY_ROAD, road);
        info.put(KEY_HOUSE, house);

        // Write the user info to the Realtime Database

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profiles").child(uid);

            // Check if the user node already exists
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("contact1", contact1);
        hashMap.put("contact2", contact2);
        hashMap.put("gender",gender);
        hashMap.put("district",gender);



        reference.updateChildren(hashMap);

        new  AestheticDialog.Builder(UpdateProfile.this, DialogStyle.FLAT, DialogType.SUCCESS)
                .setTitle("Updated")
                .setMessage("Profile Updated Successfully")
                .setCancelable(false)
                .setDarkMode(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(new OnDialogClickListener() {
                    @Override
                    public void onClick(AestheticDialog.Builder dialog) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(UpdateProfile.this, HomeScreenUser.class));
                    }
                })
                .show();

    }

    private void start_HomeScreenUser_Activity() {
        // Start HomeScreenUser activity
        Intent intent = new Intent(this, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}
