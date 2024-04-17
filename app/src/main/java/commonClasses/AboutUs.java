/*
to show the users about the developers of this app.
 */

package commonClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.R;
import com.example.android.databinding.ActivityAboutUsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import navigationBars.DrawerBaseActivity;

public class AboutUs extends DrawerBaseActivity {
    ActivityAboutUsBinding activityAboutUsBinding;

    LinearLayout fahad; // to work with Fahad
    RelativeLayout sanzida; // to work with Sanzida

    Animation left, right; // creating anim variables

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView home, liveChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutUsBinding=ActivityAboutUsBinding.inflate(getLayoutInflater()) ;
        setContentView(activityAboutUsBinding.getRoot());
        allocateActivityTitle("About Us");



    }
}