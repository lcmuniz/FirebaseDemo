package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.controller.IClienteListener;
import com.eficaztech.firebasedemo.controller.PedidoController;
import com.eficaztech.firebasedemo.controller.IEmpresaListener;
import com.eficaztech.firebasedemo.model.Cliente;
import com.eficaztech.firebasedemo.model.Empresa;
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

    private EmpresaController empresaController;
    private ClienteController clienteController;
    private PedidoController pedidoController;

    private ArrayList<Empresa> empresas;
    private ArrayAdapter<Empresa> empresasListAdapter;
    private ArrayList<Cliente> clientes;
    private ArrayAdapter<Cliente> clientesListAdapter;

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

    private void loadPedido() {

        Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        if (pedido == null) {
            pedido = new Pedido();
            pedido.setCodigo(UUID.randomUUID().toString());
            pedido.setEmpresa(empresas.get(0));
            pedido.setCliente(cliente);
        }

        codigoEditText.setText(pedido.getCodigo());

        int posEmp = empresas.indexOf(pedido.getEmpresa());
        empresasSpinner.setSelection(posEmp);

        int posCli = clientes.indexOf(pedido.getCliente());
        clientesSpinner.setSelection(posCli);

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
        loadPedido();
    }

}
