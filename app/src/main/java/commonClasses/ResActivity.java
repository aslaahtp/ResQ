package commonClasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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

public class ResActivity extends AppCompatActivity {
    List<ResModel> packlist;
    ResAdapter framesAdapter;
    RecyclerView recyclerView;
    AppCompatButton bt_req;
    String e_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sos);

        packlist = new ArrayList<ResModel>();
        recyclerView = findViewById(R.id.recyclerView);
        bt_req = findViewById(R.id.bt_submit);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        e_id= sharedPreferences.getString("event_id", "");
        addPacks();

        bt_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResActivity.this,AdsSosActivity.class));
            }
        });

    }

    private void addPacks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("res");
        // Toast.makeText(this, "ethi", Toast.LENGTH_SHORT).show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                packlist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ResModel posts = postSnapshot.getValue( ResModel.class);
                 //   if (posts.getE_id().equals(e_id)){
                        packlist.add(posts);
                 //   }

                    //  Toast.makeText(FramesActivity.this, "" + packlist.size(), Toast.LENGTH_SHORT).show();
                }


                ResAdapter eventsAdapter = new ResAdapter(packlist, ResActivity.this);
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