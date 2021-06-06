package com.hamsofts.startips;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.messaging.FirebaseMessaging;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseMessaging.getInstance().subscribeToTopic("startips");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_view);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.about) {
           /* AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Jackpot Predictions");
            try {
                alert.setMessage("Version " + getApplication().getPackageManager().getPackageInfo(getPackageName(), 0).versionName +
                        "\n" + " Automata Software. \n" + "\n" +
                        "All rights reserved \n"
                );
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            alert.show();*/
            // Inflate the about message contents
            View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

            // When linking text, force to always use default color. This works
            // around a pressed color state bug.
            TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
            TextView textView1 = (TextView) messageView.findViewById(R.id.about_description);
            int defaultColor = textView.getResources().getColor(R.color.colorBlack);
            int defaultColor1 = textView1.getResources().getColor(R.color.colorBlack);
            //int defaultColor = textView.getTextColors().getDefaultColor();
            textView.setTextColor(defaultColor);
            textView1.setTextColor(defaultColor1);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(R.string.app_name);
            builder.setView(messageView);
            builder.create();
            builder.show();
        } else if (id == R.id.feedback) {
            startActivity(new Intent(Home.this, Feedback.class));
        } else if (id == R.id.rate) {

            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Home.this, "Unable to find play store", Toast.LENGTH_SHORT).show();
            }

        } else if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Win Big with the best football tips app on playstore . Download here https://play.google.com/store/apps/details?id=com.hamsofts.startips";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Best Football Predictions App on Play Store");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(sharingIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayView(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = "";

        switch (viewId) {
            case R.id.nav_sportpesa:
                fragment = new Daily_Tips();
                title = "Daily Tips";
                /*mInterstitialAd = createNewIntAd();
                loadIntAdd();*/
                break;
            case R.id.nav_bet9ja:
                fragment = new Two_Odds();
                title = "Daily Two Plus Tips";
                /*mInterstitialAd = createNewIntAd();
                loadIntAdd();*/
                break;
            case R.id.nav_betin:
                fragment = new Mega_Jackpot_Tips();
                title = "Mega Jackpot Tips";
                /*mInterstitialAd = createNewIntAd();
                loadIntAdd();*/
                break;
            /*case R.id.nav_betway:
                fragment = new Vip_Archive();
                title = "VIP Archive";
                break;*/
           /* case R.id.nav_joinvip:
                fragment = new JoinVip();
                title = "Join VIP";
                break;
            case R.id.nav_telegram:
                fragment = new Telegram_Websites();
                title = "Telegram Channel";
                mInterstitialAd = createNewIntAd();
                loadIntAdd();
                break;*/
            case R.id.nav_livescore:
                fragment = new Live_Scores();
                /*mInterstitialAd = createNewIntAd();
                loadIntAdd();*/
                title = "SofaScore Live Score";
                break;
            default:
                fragment = new Daily_Tips();
                title = "Daily Super Tips";
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}
