package com.banice.laundry.user.Fragments;

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
import com.banice.laundry.user.Adapters.AdapterRequest;
import com.banice.laundry.user.Models.CustomerModel;
import com.banice.laundry.user.Models.ModelUser;
import com.banice.laundry.user.Models.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class My_RequestFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterRequest adapterRequest;
    List<RequestModel> requestList;


    public My_RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my__request, container, false);

        //init recycleview
        recyclerView = view.findViewById(R.id.users_recyclerview);

        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        requestList = new ArrayList<>();
        getAllMyRequests();

        return view;

    }

    private void getAllMyRequests(){
        final String fUser = FirebaseAuth.getInstance().getUid();



                     //get path of database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests");

        //get all data form path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);

                    //get all user
//                    if (!modelUser.getUid().equals(fUser)){
//                        userList.add(modelUser);
//                    }

                    if(requestModel.getRequest_customer_id().equals(fUser)) {
                        requestList.add(requestModel);
                    }
                    //adapter
                    adapterRequest = new AdapterRequest(getActivity(), requestList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterRequest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}



