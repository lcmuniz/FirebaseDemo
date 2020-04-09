package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.controller.IClienteListener;
import com.eficaztech.firebasedemo.controller.IPedidoListener;
import com.eficaztech.firebasedemo.controller.PedidoController;
import com.eficaztech.firebasedemo.controller.IEmpresaListener;
import com.eficaztech.firebasedemo.model.Cliente;
import com.eficaztech.firebasedemo.model.Empresa;
import com.eficaztech.firebasedemo.model.ItemPedido;
import com.eficaztech.firebasedemo.model.Pedido;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PedidoActivity extends AppCompatActivity {

    private EditText codigoEditText;
    private Spinner empresasSpinner;
    private Spinner clientesSpinner;
    private ListView itensPedidoListView;

    private EmpresaController empresaController;
    private ClienteController clienteController;
    private PedidoController pedidoController;

    private ArrayList<Empresa> empresas;
    private ArrayAdapter<Empresa> empresasListAdapter;
    private ArrayList<Cliente> clientes;
    private ArrayAdapter<Cliente> clientesListAdapter;
    private List<ItemPedido> itensPedido;
    private ArrayAdapter<ItemPedido> itensPedidoListAdapter;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        EventBus.getDefault().register(this);

        setComponents();
        setControllers();

        configEmpresasSpinner();
        configClientesSpinner();

        loadEmpresas();

    }

    private void setComponents() {
        codigoEditText = findViewById(R.id.codigoEditText);
        empresasSpinner = findViewById(R.id.empresasSpinner);
        clientesSpinner = findViewById(R.id.clientesSpinner);
        itensPedidoListView = findViewById(R.id.itensPedidoListView);
    }

    private void setControllers() {
        empresaController = new EmpresaController();
        clienteController = new ClienteController();
        pedidoController = new PedidoController();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void configEmpresasSpinner() {
        empresas = new ArrayList<>();
        empresasListAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_1, empresas);
        empresasSpinner.setAdapter(empresasListAdapter);
    }


    private void configClientesSpinner() {
        clientes = new ArrayList<>();
        clientesListAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
        clientesSpinner.setAdapter(clientesListAdapter);
    }


    private void loadEmpresas() {
        empresaController.findAll(new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                EventBus.getDefault().post(new SuccessEmpresas(empresas));
            }
        });
    }

    private void loadClientes() {
        clienteController.findAll(new IClienteListener() {
            @Override
            public void onSuccess(List<Cliente> clientes) {
                EventBus.getDefault().post(new SuccessClientes(clientes));
            }
        });
    }

    private void loadPedido(Pedido pedido) {

        codigoEditText.setText(pedido.getCodigo());

        if (pedido.getEmpresa() == null) {
            pedido.setEmpresa(empresas.get(0));
        }

        int posEmp = empresas.indexOf(pedido.getEmpresa());
        empresasSpinner.setSelection(posEmp);

        int posCli = clientes.indexOf(pedido.getCliente());
        clientesSpinner.setSelection(posCli);

        loadItensPedido(pedido.getItensPedido());

    }

    private void loadItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
        itensPedidoListAdapter = new ArrayAdapter<ItemPedido>(this, android.R.layout.simple_list_item_1, this.itensPedido);
        itensPedidoListView.setAdapter(itensPedidoListAdapter);
        itensPedidoListView.setOnItemClickListener((adapterView, view, position, l) -> onClickItensPedidoListView(position));
    }

    private void onClickItensPedidoListView(int position) {
        Intent intent = new Intent(this, ItemPedidoActivity.class);
        intent.putExtra("itemPedido", itensPedido.get(position));
        intent.putExtra("pedido", pedido);
        startActivity(intent);
    }

    public void onClickSalvarButton(View view) {

        Pedido pedido = new Pedido();
        pedido.setCodigo(codigoEditText.getText().toString());
        pedido.setCliente(clientes.get(clientesSpinner.getSelectedItemPosition()));
        pedido.setEmpresa(empresas.get(empresasSpinner.getSelectedItemPosition()));
        pedidoController.cadastrar(pedido);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Pedido pedido = new Pedido();
        pedido.setCodigo(codigoEditText.getText().toString());

        pedidoController.excluir(pedido);

        finish();

    }

    public void onClickAdicionarItemButton(View view) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setCodigo(UUID.randomUUID().toString());
        itemPedido.setQuantidade(1);

        Intent intent = new Intent(this, ItemPedidoActivity.class);
        intent.putExtra("itemPedido", itemPedido);
        intent.putExtra("pedido", pedido);
        startActivity(intent);
    }

    public static class ItemPedidoChanged {
    }

    @Subscribe
    public void on(ItemPedidoChanged event) {
        pedidoController.findByCodigo(pedido.getCodigo(), new IPedidoListener() {
            @Override
            public void onSuccess(Pedido pedido) {
                loadPedido(pedido);
            }
        });
    }

    private class SuccessEmpresas {
        private final List<Empresa> empresas;

        public SuccessEmpresas(List<Empresa> empresas) {
            this.empresas = empresas;
        }
    }

    @Subscribe
    public void on(SuccessEmpresas success) {
        empresas.clear();
        empresas.addAll(success.empresas);
        empresasListAdapter.notifyDataSetChanged();
        loadClientes();
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
        pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        loadPedido(pedido);
    }

}
