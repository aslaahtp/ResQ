package commonClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

// Your updated RecyclerView Adapter
public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> {
    private List<ResModel> itemList;
    private Context context;
    FirebaseAuth mAuth;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private int selectedItemPosition = -1;
    int initialSelectedItemPosition = 2;
    private OnItemSelectedListener listener;

    public ResAdapter(List<ResModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sos, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResModel item = itemList.get(position);



        String price = String.valueOf(item.getName());
        String dollar = String.valueOf(item.getQty());
       // Glide.with(context)
            //    .load(item.getUrl())
            //    .fitCenter()
             //   .into(holder.imageView);

        holder.tv_price.setText(price);
        holder.tv_title.setText(dollar );


        // Set background color based on selection state


        // Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    notifyDataSetChanged();
                   // Toast.makeText(context, "Price: " + price, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                   // SharedPreferences.Editor editor = sharedPreferences.edit();
                  //  editor.putString("frameurl",item.getUrl());

                  //  editor.putString("frametitle", item.getTitle());
                  //  editor.putString("frameprice", String.valueOf(item.getPrice()));

                  //  editor.apply();
                   // context.startActivity(new Intent(context, PrintActivity.class));

                   // editor.apply();
                    // Pass selected item's price value to the listener


            }
        });
        holder.bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
               // SharedPreferences.Editor editor = sharedPreferences.edit();
               // editor.putString("frameurl",item.getUrl());

               // editor.putString("frametitle", item.getTitle());
               // editor.putString("frameprice", String.valueOf(item.getPrice()));



               // editor.apply();
               // context.startActivity(new Intent(context, PrintActivity.class));
            }
        });
        // Other methods remain unchanged
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        TextView tv_title, tv_price;
        LinearLayout bt_order;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewHorizontal);
            tv_title = itemView.findViewById(R.id.tv_title);
            bt_order= itemView.findViewById(R.id.ln_order);

            tv_price = itemView.findViewById(R.id.tv_price);

        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String price,String coins,String extra);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;


        } else {
            return true;

        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            }
        } else {
            if (context instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void resetBackgroundColors(int selectedPosition) {
        for (int i = 0; i < itemList.size(); i++) {
            if (i != selectedPosition) {
             //   itemList.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectedItemPosition(int position) {
        selectedItemPosition = position;
        notifyDataSetChanged();
    }
}
