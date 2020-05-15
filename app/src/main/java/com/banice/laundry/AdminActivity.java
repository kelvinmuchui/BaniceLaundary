package com.banice.laundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.banice.laundry.Provider.Adapter.AdapterServices;
import com.banice.laundry.Provider.CreateServiceActivity;
import com.banice.laundry.Provider.Model.ServiceModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    FloatingActionButton fad;

    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    AdapterServices adapterServices;
    List<ServiceModel> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseAuth = FirebaseAuth.getInstance();

        //init recycleview
        recyclerView = findViewById(R.id.providerService_recyclerview);
        fad = findViewById(R.id.createService);
        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        serviceList = new ArrayList<>();
        getMyserivices();


        fad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(AdminActivity.this, CreateServiceActivity.class);
                startActivity(create);

            }
        });
    }

    private void getMyserivices() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String providerId = currentFirebaseUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Services");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ServiceModel requestModel = ds.getValue(ServiceModel.class);

                    //get all user
//                    if (!modelUser.getUid().equals(fUser)){
//                        userList.add(modelUser);
//                    }

                    //  if (requestModel.getService_id().equals(providerId)) {
                    serviceList.add(requestModel);
                    //}
                    //adapter
                    adapterServices = new AdapterServices(AdminActivity.this, serviceList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterServices);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

}
