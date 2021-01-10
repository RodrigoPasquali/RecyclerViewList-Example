package com.example.telynet.Presenter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telynet.Model.Client;
import com.example.telynet.Presenter.Interface.MainActivityPresenterInterface;
import com.example.telynet.R;
import com.example.telynet.View.MainActivity;
import com.example.telynet.View.Utils.ClientRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivityPresenter implements MainActivityPresenterInterface, AdapterView.OnItemSelectedListener {
    private MainActivity mainActivityView;
    private Context context;

    private ClientRecyclerViewAdapter recyclerAdapter;
    private Spinner sortSpinner;
    private RecyclerView recyclerView;
    private Button btnAscendingSort;
    private Button btnDescendingSort;
    private List clients;
    private List filterClients;
    private String codeString;
    private String nameString;
    private boolean firstTime = true;

    public MainActivityPresenter(Context context, MainActivity view) {
        this.mainActivityView = view;
        this.context = context;

        generateClientListExample();
        filterClients = clients;
        codeString = context.getString(R.string.code);
        nameString = context.getString(R.string.name);

        recyclerView = view.findViewById(R.id.telynet_recicler_view);
        btnAscendingSort = view.findViewById(R.id.btn_descending_sort);
        btnDescendingSort = view.findViewById(R.id.btn_ascending_sort);

        recyclerViewAction();
        spinnerAction();

        firstTime = false;
    }

    private void generateClientListExample() {
        clients = new ArrayList<Client>();
        clients.add(new Client("Roberto", "45", "12345678", "roberto@mail.com", "SI"));
        clients.add(new Client("Carla", "76", "23456781", "carla@mail.com", "SI"));
        clients.add(new Client("Simon", "02", "34567812", "simon@mail.com", "NO"));
        clients.add(new Client("Maria", "12", "45678123", "maria@mail.com",  "SI"));
        clients.add(new Client("Juan", "54", "56781234", "juan@mail.com",  "NO"));
        clients.add(new Client("Pedro", "22", "67812345", "pedro@mail.com", "NO"));
        clients.add(new Client("Cecilia", "07", "78123456", "cecilia@mail.com", "NO"));
        clients.add(new Client("Santiago", "99", "81234567", "santiago@mail.com", "NO"));
        clients.add(new Client("Juliana", "01", "19234567", "cecilia@mail.com", "SI"));
        clients.add(new Client("Esteban", "87", "19345678", "esteban@mail.com", "NO"));
        clients.add(new Client("Daiana", "15", "19456782", "daiana@mail.com", "SI"));
        clients.add(new Client("Natalia", "39", "19567823", "natalia@mail.com", "SI"));
    }

    private void recyclerViewAction() {
        recyclerAdapter = new ClientRecyclerViewAdapter(clients, context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if(firstTime) {
            DividerItemDecoration did = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            did.setDrawable(context.getResources().getDrawable(R.drawable.divider_decorator_item));
            recyclerView.addItemDecoration(did);
        }

        recyclerView.setAdapter(recyclerAdapter);
    }

    private void spinnerAction() {
        String[] ordersList = new String[] {codeString, nameString};
        ArrayAdapter arrayAdapterSpinner = new ArrayAdapter(context , R.layout.spinner_item, ordersList);
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner = mainActivityView.findViewById(R.id.spinner_sort);
        sortSpinner.setOnItemSelectedListener(this);
        sortSpinner.setAdapter(arrayAdapterSpinner);
    }

    private void applyAscendingSortByCode(List clients) {
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getCode().compareTo(client2.getCode());
            }
        });
    }

    private void applyDescendingSortByCode(List clients) {
        applyAscendingSortByCode(clients);
        Collections.reverse(clients);
    }

    private void applyAscendingSortByName(List clients) {
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client cliente1, Client client2) {
                return cliente1.getName().compareTo(client2.getName());
            }
        });
    }

    private void applyDescendingSortByName(List clients) {
        applyAscendingSortByName(clients);
        Collections.reverse(clients);
    }

    @Override
    public void applyAscendingSort() {
        if (sortSpinner.getSelectedItem().toString() == codeString) {
            applyAscendingSortByCode(filterClients);
        } else {
            applyAscendingSortByName(filterClients);
        }

        recyclerAdapter.notifyDataSetChanged();

        btnDescendingSort.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
        btnAscendingSort.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
    }

    @Override
    public void applyDescendingSort() {
        if(sortSpinner.getSelectedItem().toString() == codeString) {
            applyDescendingSortByCode(filterClients);
        } else {
            applyDescendingSortByName(filterClients);
        }

        recyclerAdapter.notifyDataSetChanged();

        btnAscendingSort.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
        btnDescendingSort.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
    }

    @Override
    public void applyVisitFilter(boolean visit, boolean notVisit) {
        filterClients = clients;
        recyclerViewAction();

        if(visit){
            filterClients = recyclerAdapter.doClientVisitFilter("SI");
        } else if(notVisit) {
            filterClients = recyclerAdapter.doClientVisitFilter("NO");
        }

        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        btnDescendingSort.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
        btnAscendingSort.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
