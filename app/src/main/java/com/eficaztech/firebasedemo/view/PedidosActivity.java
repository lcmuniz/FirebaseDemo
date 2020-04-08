package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.controller.IClienteListener;
import com.eficaztech.firebasedemo.controller.PedidoController;
import com.eficaztech.firebasedemo.controller.IPedidoListener;
import com.eficaztech.firebasedemo.model.Cliente;
import com.eficaztech.firebasedemo.model.Pedido;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PedidosActivity extends AppCompatActivity {

    private Spinner clientesSpinner;
    private ListView pedidosListView;

    private ClienteController clienteController;
    private PedidoController pedidoController;

    private ArrayList<Cliente> clientes;
    private ArrayAdapter<Cliente> clientesListAdapter;
    private ArrayList<Pedido> pedidos;
    private ArrayAdapter<Pedido> pedidosListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        EventBus.getDefault().register(this);

        setComponents();
        setControllers();

        configSpinner();
        configListView();

        loadClientes();

    }

    private void setComponents() {
        clientesSpinner = findViewById(R.id.clientesSpinner);
        pedidosListView = findViewById(R.id.pedidosListView);
    }

    private void setControllers() {
        clienteController = new ClienteController();
        pedidoController = new PedidoController();
    }

    private void loadClientes() {
        clienteController.findAll(new IClienteListener() {
            @Override
            public void onSuccess(List<Cliente> clientes) {
                EventBus.getDefault().post(new SuccessClientes(clientes));
            }
        });
    }

    private void configSpinner() {
        clientes = new ArrayList<>();
        clientesListAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
        clientesSpinner.setAdapter(clientesListAdapter);
        clientesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                onClickClientesSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClickClientesSpinner(int position) {
        Cliente cliente = clientes.get(position);
        loadPedidos(cliente);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadPedidos(Cliente cliente) {
        pedidoController.findAllByCliente(cliente, new IPedidoListener() {
            @Override
            public void onSuccess(List<Pedido> pedidos) {
                EventBus.getDefault().post(new SuccessPedidos(pedidos));
            }
        });
    }

    private void configListView() {
        pedidos = new ArrayList<>();
        pedidosListAdapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, pedidos);
        pedidosListView.setAdapter(pedidosListAdapter);
        pedidosListView.setOnItemClickListener((adapterView, view, position, l) -> onClickPedidosListView(position));
    }

    private void onClickPedidosListView(int position) {
        Intent intent = new Intent(this, PedidoActivity.class);
        intent.putExtra("pedido", pedidos.get(position));
        startActivity(intent);
    }

    public void onClickAdicionarButton(View view) {
        Intent intent = new Intent(this, PedidoActivity.class);
        int position = clientesSpinner.getSelectedItemPosition();
        intent.putExtra("cliente", clientes.get(position));
        startActivity(intent);
    }

    private class SuccessPedidos {
        private final List<Pedido> pedidos;

        public SuccessPedidos(List<Pedido> pedidos) {
            this.pedidos = pedidos;
        }
    }

    @Subscribe
    public void on(SuccessPedidos success) {
        pedidos.clear();
        pedidos.addAll(success.pedidos);
        pedidosListAdapter.notifyDataSetChanged();
    }

    private class SuccessClientes {
        private final List<Cliente> clientes;

        public SuccessClientes(List<Cliente> clientes) {
            this.clientes = clientes;
        }
    }

    @Subscribe
    public void on(SuccessClientes success) {
        clientes.clear();
        clientes.addAll(success.clientes);
        clientesListAdapter.notifyDataSetChanged();
        loadPedidos(clientes.get(0));
    }

}
