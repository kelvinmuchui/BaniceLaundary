package com.banice.laundry.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.banice.laundry.Launcher;
import com.banice.laundry.R;
import com.banice.laundry.user.Fragments.Contact_usFragment;
import com.banice.laundry.user.Fragments.My_RequestFragment;
import com.banice.laundry.user.Fragments.ServicesFragment;
import com.banice.laundry.user.Fragments.providerFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class The_user_profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name, email;
    CircleImageView image;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid, uri;
    NavigationView navigationView;
    boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_user_profile2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment frag = null;
        frag = new ServicesFragment();
        if (frag != null) {
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.content, frag);
            fr.commit();
            getSupportActionBar().setTitle("Services");
        }

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        name = header.findViewById(R.id.name);
        email = header.findViewById(R.id.email);
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");
        image = header.findViewById(R.id.circle_pic);


        databaseReference.child(userid);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.the_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutus) {

            firebaseAuth.signOut();
            Intent intent = new Intent(this, Launcher.class);
            startActivity(intent);
            finish();


            return true;
        } else if (id == R.id.rateus) {





        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.providers) {
            fragment = new providerFragment();

            getSupportActionBar().setTitle("Providers");

        } else if (id == R.id.contactus) {
            fragment = new Contact_usFragment();
            getSupportActionBar().setTitle("Contact Us");
        } else if (id == R.id.completeRequest) {

            fragment = new My_RequestFragment();
            getSupportActionBar().setTitle("My Request");
        } else if (id == R.id.payment) {

            fragment = new ServicesFragment();
            getSupportActionBar().setTitle("Services");
        }

        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.replace(R.id.content, fragment);
        f.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish();
                return;
            }

            this.exit = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

}