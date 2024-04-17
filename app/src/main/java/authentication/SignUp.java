/*
this class is used to register a new account.
 */

package authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import commonClasses.UpdateProfile;
import user.HomeScreenUser;
import com.example.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextView signIn;
    Button create;
    ImageView google, facebook;
    EditText email, password, confirmPassword;

    private FirebaseAuth mAuth; // to work with firebase

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.theme_color));

        mAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.signInID); // to go to the signIn page
        create = findViewById(R.id.createID); // to create a new account

        email = findViewById(R.id.emailID); // input email
        password = findViewById(R.id.passwordID); // input password
        confirmPassword = findViewById(R.id.confirmPasswordID); // retype of the password for checking match

        // setting on click listener
        create.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // if tries to go to the sign in page from sign up page
        if (v.getId() == R.id.signInID) {
            start_SignIn_activity();
        }

        // if anyone tries to create a new account with email and pass
        if (v.getId() == R.id.createID) {
            createAccount("email");
        }
    }

    // to create a new account
    private void createAccount(String type) {
        String emailText = email.getText().toString().trim(); // text of email (input)
        String passText = password.getText().toString(); // text of password (input)
        String confirmPassText = confirmPassword.getText().toString();

        // checking for email input
        if(emailText.isEmpty()) {
            email.setError("Enter an email address");
            email.requestFocus();
            return;
        }

        // checking the email is valid or not
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Enter a valid email address");
            email.requestFocus();
            return;
        }

        // checking the validity of the password
        if(passText.isEmpty() || passText.length() < 8) {
            password.setError("Minimum length is 8");
            password.requestFocus();
            return;
        }

        if (!passText.equals(confirmPassText)) {
            password.setError("Password didn't match!");
            password.requestFocus();
            return;
        }

        if (type.equals("email")) createWithEmail(emailText, passText); // with email and pass
    }

    // if the user wants to create account with email and pass
    private void createWithEmail(String emailText, String passText) {
        // if every info is ok, creating a new account.
        mAuth.createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Registration is successful", Toast.LENGTH_SHORT).show();

                            email.setText("");
                            password.setText("");
                            confirmPassword.setText("");



                            writeOnVolunteerList();
                            start_UpdateProfile_activity();
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Already exists! Try sign in.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    /*
    if it is a new user then we will write on volunteer list and. mark him as a non-volunteer.
    later he can register for volunteer.
     */
    private void writeOnVolunteerList() {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DocumentReference documentReference = db.collection("Volunteer List").document(uid); // to write on
                // volunteer list


            Map<String, Object> info = new HashMap<>();

            info.put("Registered", "no"); // no means not a volunteer
            documentReference.set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // nothing to show
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // nothing to show
                }
            });
        } catch (Exception e) {
            // no need to show the user.
        }
    }

    private void start_SignIn_activity() {
        Intent intent = new Intent(this, SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void start_HomeScreenUser_activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(this, HomeScreenUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        //finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void start_UpdateProfile_activity() {
        HomeScreenUser.makeBackPressedCntZero();
        Intent intent = new Intent(this, UpdateProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}