package com.banice.laundry.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.banice.laundry.R;
import com.banice.laundry.user.Adapters.AdapterProviders;
import com.banice.laundry.user.Models.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProviderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterProviders adapterUsers;
    List<ModelUser> userList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider2);

        //init recycleview
        recyclerView = findViewById(R.id.users_recyclerview);

        //set its properties
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //init user list
        userList = new ArrayList<>();

        //get All Users

        getAllUsers();
    }


     private void getAllUsers(){
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Provider");

        //get all data form path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                  //  ModelUser modelUser = ds.getValue(ModelUser.class);
                    ModelUser modelUser = ds.getValue(ModelUser.class);
                    userList.add(modelUser);



                    //get all user
//                    if (!modelUser.getUid().equals(fUser)){
//                        userList.add(modelUser);
//                    }
                  //  userList.add(modelUser);
                    //adapter
                    adapterUsers = new AdapterProviders(ProviderActivity.this,userList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
