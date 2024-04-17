package commonClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import user.HomeScreenUser;

public class SosActivity extends AppCompatActivity {
    List<EventsModel> packlist;
    EventsAdapter framesAdapter;
    RecyclerView recyclerView;
    String e_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sos);

        packlist = new ArrayList<EventsModel>();
        recyclerView = findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        e_id= sharedPreferences.getString("event_id", "");
        addPacks();

    }

    private void addPacks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("sos");
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


                EventsAdapter eventsAdapter = new EventsAdapter(packlist, SosActivity.this);
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