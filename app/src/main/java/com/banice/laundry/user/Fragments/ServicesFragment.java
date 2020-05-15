package com.banice.laundry.user.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banice.laundry.Provider.Adapter.AdapterServices;
import com.banice.laundry.R;
import com.banice.laundry.user.Adapters.AdapterService;
import com.banice.laundry.user.Models.Services;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterService adapterService;
    List<Services>servicesList;




    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_services, container, false);
        recyclerView = view.findViewById(R.id.service_recyclerview);

        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        servicesList = new ArrayList<>();

        getAllServices();
        return view;
    }

    private void getAllServices() {

        //get path of database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Services");

        //get all data form path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //  ModelUser modelUser = ds.getValue(ModelUser.class);
                    Services services = ds.getValue(Services.class);
                    servicesList.add(services);



                    //get all user
//                    if (!modelUser.getUid().equals(fUser)){
//                        userList.add(modelUser);
//                    }
                    //  userList.add(modelUser);
                    //adapter
                    adapterService = new AdapterService(getActivity(), servicesList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterService);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
