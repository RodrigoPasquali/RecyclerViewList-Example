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

//public class ClientRecyclerViewAdapter extends RecyclerView.Adapter<ClientRecyclerViewAdapter.ViewHolder> implements Filterable {
public class ClientRecyclerViewAdapter extends RecyclerView.Adapter<ClientRecyclerViewAdapter.ViewHolder>{
    private List originalClientsList;
    private List filteredClientsList;
//    private List filterClients;
    private Context context;
//    private VisitClientFilter visitClientFilter;

    public ClientRecyclerViewAdapter(List clients, Context context) {
        this.filteredClientsList = new LinkedList<Client>();
        this.originalClientsList = clients;
        this.context = context;
//        filterClients = new LinkedList<Client>();
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
        holder.tvVisitado.setText(client.getVisit());

        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(client.getPhone());
            }
        });
    }

    @Override
    public int getItemCount() {
        return originalClientsList.size();
    }

 /*
    @Override
    public Filter getFilter() {
        if(visitClientFilter == null)
            visitClientFilter = new VisitClientFilter(this, clients);
        return visitClientFilter;
    }
*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPhone;
        public TextView tvName;
        public TextView tvCode;
        public TextView tvPhone;
        public TextView tvEmail;
        public TextView tvVisitado;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivPhone = (ImageView) itemView.findViewById(R.id.iv_phone);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvCode = (TextView) itemView.findViewById(R.id.tv_code);
            this.tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            this.tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            this.tvVisitado = (TextView) itemView.findViewById(R.id.tv_visit);
        }
    }

    public List visitFilter(String isVisited) {
        if(isVisited.toUpperCase() == "SI") {
            for(int i = 0; i < originalClientsList.size(); i++) {
                Client actualClient = (Client) originalClientsList.get(i);
                if(actualClient.getVisit() == "SI") {
                    filteredClientsList.add(actualClient);
                }
            }
            originalClientsList = filteredClientsList;
        } else if(isVisited.toUpperCase() == "NO") {
            for(int i = 0; i < originalClientsList.size(); i++) {
                Client actualClient = (Client) originalClientsList.get(i);
                if(actualClient.getVisit() == "NO") {
                    filteredClientsList.add(actualClient);
                }
            }
            originalClientsList = filteredClientsList;
        } else {
            filteredClientsList = originalClientsList;
        }
        return filteredClientsList;
    }

    private void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }
/*
    private static class VisitClientFilter extends Filter {

        private final ClientRecyclerViewAdapter adapter;

        private final List<Client> originalList;

        private final List filteredList;

        private VisitClientFilter(ClientRecyclerViewAdapter adapter, List originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final Client client : originalList) {
                    if (client.getVisit().contains(filterPattern)) {
                        filteredList.add(client);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filterClients.clear();
            adapter.filterClients.addAll((ArrayList<Client>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
 */
}
