package com.banice.laundry.user.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class providerFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterProviders adapterUsers;
    List<ModelUser> userList;


    public  providerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_provider, container, false);

        //init recycleview
        recyclerView = view.findViewById(R.id.users_recyclerview);

        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //init user list
        userList = new ArrayList<>();

        //get All Users

        getAllUsers();
        return view;

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
                    adapterUsers = new AdapterProviders(getActivity(), userList);
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

