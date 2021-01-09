package com.example.telynet.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.telynet.Model.Client;
import com.example.telynet.R;
import com.example.telynet.View.Utils.ClientRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;
    private Button btnAscendingOrder;
    private Button btnDescendingOrder;
    private Spinner orderSpinner;
    private ClientRecyclerViewAdapter recyclerAdapter;
    private RadioGroup rdgVisit;
    private RadioButton rdVisitYes;
    private RadioButton rdVisitNo;
    private RadioButton rdVisitAll;
    private List clients;
    private List filterClients;
    private String codeString;
    private String nameString;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateClientListExample();

        filterClients = clients;

        recyclerView = (RecyclerView) findViewById(R.id.telynet_recicler_view);
        btnAscendingOrder = (Button) findViewById(R.id.btn_ascending_order);
        btnDescendingOrder = (Button) findViewById(R.id.btn_descending_order);
        orderSpinner = (Spinner) findViewById(R.id.spinner_order);
        rdgVisit = (RadioGroup) findViewById(R.id.rd_group_visit);
        rdVisitYes = (RadioButton) findViewById(R.id.rb_visit_yes);
        rdVisitNo = (RadioButton) findViewById(R.id.rb_visit_no);
        rdVisitAll = (RadioButton) findViewById(R.id.rb_visit_all);

        codeString = getString(R.string.code);
        nameString = getString(R.string.name);

        recyclerViewAction();

        spinnertAction();

        buttonAscendingOrderAction();

        buttonDescendingOrderAction();

        radioButtonAction();

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
        recyclerAdapter = new ClientRecyclerViewAdapter(clients, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(firstTime) {
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        }
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void spinnertAction() {
        String[] ordersList = new String[] {codeString, nameString};
        orderSpinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapterSpinner = new ArrayAdapter(this,android.R.layout.simple_spinner_item, ordersList);
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(arrayAdapterSpinner);
    }

    private void buttonAscendingOrderAction() {
        btnAscendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderSpinner.getSelectedItem().toString() == codeString) {
                    applyCodeAscendingtOrden(filterClients);
                    recyclerAdapter.notifyDataSetChanged();

                } else {
                    applyNameAscendingtOrden(filterClients);
                    recyclerAdapter.notifyDataSetChanged();

                }
                btnAscendingOrder.setBackgroundColor(getResources().getColor(R.color.teal_700));
                btnDescendingOrder.setBackgroundColor(getResources().getColor(R.color.purple_200));
            }
        });
    }

    private void buttonDescendingOrderAction() {
        btnDescendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderSpinner.getSelectedItem().toString() == codeString) {
                    applyCodeDescendingOrden(filterClients);
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    applyNameDescendingOrden(filterClients);
                    recyclerAdapter.notifyDataSetChanged();
                }
                btnDescendingOrder.setBackgroundColor(getResources().getColor(R.color.teal_700));
                btnAscendingOrder.setBackgroundColor(getResources().getColor(R.color.purple_200));
            }
        });
    }

    private void applyCodeAscendingtOrden(List clients) {
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getCode().compareTo(client2.getCode());
            }
        });
    }

    private void applyCodeDescendingOrden(List clients) {
        applyCodeAscendingtOrden(clients);
        Collections.reverse(clients);
    }

    private void applyNameAscendingtOrden(List clients) {
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client cliente1, Client client2) {
                return cliente1.getName().compareTo(client2.getName());
            }
        });
    }

    private void applyNameDescendingOrden(List clients) {
        applyNameAscendingtOrden(clients);
        Collections.reverse(clients);
    }

    private void radioButtonAction() {
        rdVisitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyVisitFilter();
            }
        });

        rdVisitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyVisitFilter();
            }
        });

        rdVisitAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyVisitFilter();
            }
        });
    }

    private void applyVisitFilter() {
        filterClients = clients;
        recyclerViewAction();

        if(rdVisitYes.isChecked()){
            filterClients = recyclerAdapter.visitFilter("SI");
        } else if(rdVisitNo.isChecked()) {
            filterClients = recyclerAdapter.visitFilter("NO");
        }

        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        btnAscendingOrder.setBackgroundColor(getResources().getColor(R.color.purple_200));
        btnDescendingOrder.setBackgroundColor(getResources().getColor(R.color.purple_200));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}