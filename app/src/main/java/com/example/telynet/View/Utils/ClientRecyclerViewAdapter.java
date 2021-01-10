package com.example.telynet.View.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.telynet.Model.Client;
import com.example.telynet.R;

import java.util.LinkedList;
import java.util.List;

public class ClientRecyclerViewAdapter extends RecyclerView.Adapter<ClientRecyclerViewAdapter.ViewHolder>{
    private List originalClientsList;
    private List filteredClientsList;
    private Context context;

    public ClientRecyclerViewAdapter(List clients, Context context) {
        this.filteredClientsList = new LinkedList<Client>();
        this.originalClientsList = clients;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Client client = (Client) originalClientsList.get(position);
        holder.tvName.setText(client.getName());
        holder.tvCode.setText(client.getCode());
        holder.tvPhone.setText(client.getPhone());
        holder.tvEmail.setText(client.getEmail());
        holder.tvVisited.setText(client.getVisit());

        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCallToClient(client.getPhone());
            }
        });
    }

    @Override
    public int getItemCount() {
        return originalClientsList.size();
    }

    public List doClientVisitFilter(String filter) {
        for(int i = 0; i < originalClientsList.size(); i++) {
            Client actualClient = (Client) originalClientsList.get(i);
            if(actualClient.getVisit() == filter) {
                filteredClientsList.add(actualClient);
            }
        }

        originalClientsList = filteredClientsList;

        return filteredClientsList;
    }

    private void makeCallToClient(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPhone;
        public TextView tvName;
        public TextView tvCode;
        public TextView tvPhone;
        public TextView tvEmail;
        public TextView tvVisited;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivPhone = (ImageView) itemView.findViewById(R.id.iv_phone);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvCode = (TextView) itemView.findViewById(R.id.tv_code);
            this.tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            this.tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            this.tvVisited = (TextView) itemView.findViewById(R.id.tv_visit);
        }
    }
}

