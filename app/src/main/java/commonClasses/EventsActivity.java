package commonClasses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import user.HomeScreenUser;

public class EventsActivity extends AppCompatActivity {

    EditText ed_name,ed_location,ed_type;

       AppCompatButton bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_events);

        ed_name=findViewById(R.id.ed_name);
        ed_location=findViewById(R.id.ed_name);
        ed_type=findViewById(R.id.ed_name);

        bt_submit=findViewById(R.id.bt_submit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("events");

                // Check if the user node already exists
                HashMap<String, Object> hashMap = new HashMap<>();
                String uniqueId = reference.push().getKey(); // Generating unique ID

                hashMap.put("name", ed_name.getText().toString());
                hashMap.put("location", ed_location.getText().toString());
                hashMap.put("type", ed_type.getText().toString());
                hashMap.put("event_id", uniqueId);

                Map<String, Object> updateData = new HashMap<>();
                updateData.put(uniqueId, hashMap);
                reference.updateChildren(updateData);

                new AestheticDialog.Builder(EventsActivity.this, DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle("Added")
                        .setMessage("Event Added Successfully")
                        .setCancelable(false)
                        .setDarkMode(true)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(new OnDialogClickListener() {
                            @Override
                            public void onClick(AestheticDialog.Builder dialog) {
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(EventsActivity.this, HomeScreenUser.class));
                            }
                        })
                        .show();

            }

            });
        }

}