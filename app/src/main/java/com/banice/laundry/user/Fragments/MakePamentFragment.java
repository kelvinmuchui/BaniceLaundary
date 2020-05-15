package com.banice.laundry.user.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banice.laundry.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakePamentFragment extends Fragment {


    public MakePamentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_pament, container, false);
    }

}
