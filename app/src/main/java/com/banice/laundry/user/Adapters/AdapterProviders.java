package com.banice.laundry.user.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banice.laundry.R;
import com.banice.laundry.user.ChooseServiceActivity;
import com.banice.laundry.user.Models.ModelUser;
import com.banice.laundry.user.ProviderServiceActivity;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdapterProviders extends RecyclerView.Adapter<AdapterProviders.MyHolder> {


    Context context;
    List<ModelUser> userList;

    public AdapterProviders(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    public AdapterProviders(ValueEventListener valueEventListener, List<ModelUser> userList) {
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate layour(row_user)

        View view = LayoutInflater.from(context).inflate(R.layout.row_users,parent, false);

        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        final String provider_id = userList.get(i).getProvider_Id();
        String userName = userList.get(i).getProvider_Name();
        String userEmail = userList.get(i).getProvider_email();


        //set data
        holder.mNameTv.setText(userName);
        holder.mEmailTv.setText(userEmail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent providerServive = new Intent(context, ProviderServiceActivity.class);
               providerServive.putExtra("provider_id", provider_id);
               context.startActivity(providerServive);


            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    //view holder class

    class MyHolder extends RecyclerView.ViewHolder{

        TextView mNameTv, mEmailTv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);


            //init views
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);
        }
    }
}
