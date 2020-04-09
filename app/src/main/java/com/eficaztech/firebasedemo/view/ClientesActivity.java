package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.controller.IClienteListener;
import com.eficaztech.firebasedemo.model.Cliente;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ClientesActivity extends AppCompatActivity {

    private ListView clientesListView;

    private ClienteController controller;

    private ArrayList<Cliente> clientes;
    private ArrayAdapter<Cliente> clientesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        EventBus.getDefault().register(this);

        setComponents();

        setController();

        configListView();
        loadClientes();

    }

    private void setController() {
        controller = new ClienteController();
    }

    private void setComponents() {
        clientesListView = findViewById(R.id.empresasListView);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadClientes() {
        controller.findAll(new IClienteListener() {
            @Override
            public void onSuccess(List<Cliente> clientes) {
                EventBus.getDefault().post(new Success(clientes));
            }
        });
    }

    private void configListView() {
        clientes = new ArrayList<>();
        clientesListAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
        clientesListView.setAdapter(clientesListAdapter);
        clientesListView.setOnItemClickListener((adapterView, view, position, l) -> onClickClientesListView(position));
    }

    private void onClickClientesListView(int position) {
        Intent intent = new Intent(this, ClienteActivity.class);
        intent.putExtra("cliente", clientes.get(position));
        startActivity(intent);
    }

    public void onClickAdicionarButton(View view) {
        Cliente cliente = new Cliente();
        Intent intent = new Intent(this, ClienteActivity.class);
        intent.putExtra("cliente", cliente);
        startActivity(intent);
    }

    private class Success {
        private final List<Cliente> clientes;

        public Success(List<Cliente> clientes) {
            this.clientes = clientes;
        }
    }

    @Subscribe
    public void on(Success success) {
        clientes.clear();
        clientes.addAll(success.clientes);
        clientesListAdapter.notifyDataSetChanged();
    }
}
