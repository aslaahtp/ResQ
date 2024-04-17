package commonClasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;




import com.example.android.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.HashMap;
import java.util.Map;

import user.HomeScreenUser;

public class AdsSosActivity extends AppCompatActivity {

    EditText ed_name,ed_location,ed_type;

    AppCompatButton bt_submit;
    String e_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_sos);

        ed_name=findViewById(R.id.ed_name);
        ed_location=findViewById(R.id.ed_name);
        ed_type=findViewById(R.id.ed_name);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        e_id= sharedPreferences.getString("event_id", "");
        bt_submit=findViewById(R.id.bt_submit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("sos");

                // Check if the user node already exists
                HashMap<String, Object> hashMap = new HashMap<>();
                String uniqueId = reference.push().getKey(); // Generating unique ID

                hashMap.put("name", ed_name.getText().toString());
                hashMap.put("location", ed_location.getText().toString());
                hashMap.put("time", ed_type.getText().toString());
                hashMap.put("e_id", e_id);

                Map<String, Object> updateData = new HashMap<>();
                updateData.put(uniqueId, hashMap);
                reference.updateChildren(updateData);

                new AestheticDialog.Builder(AdsSosActivity.this, DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle("Added")
                        .setMessage("Sos Message Added Successfully")
                        .setCancelable(false)
                        .setDarkMode(true)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(new OnDialogClickListener() {
                            @Override
                            public void onClick(AestheticDialog.Builder dialog) {
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(AdsSosActivity.this, HomeScreenUser.class));
                            }
                        })
                        .show();

            }

        });
    }


}