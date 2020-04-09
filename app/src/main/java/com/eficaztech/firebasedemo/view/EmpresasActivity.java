package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.controller.IEmpresaListener;
import com.eficaztech.firebasedemo.model.Empresa;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class EmpresasActivity extends AppCompatActivity {

    private ListView empresasListView;

    private EmpresaController controller;

    private ArrayList<Empresa> empresas;
    private ArrayAdapter<Empresa> empresasListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

        EventBus.getDefault().register(this);

        setComponents();
        setController();

        configListView();
        loadEmpresas();

    }

    private void setComponents() {
        empresasListView = findViewById(R.id.empresasListView);
    }

    private void setController() {
        controller = new EmpresaController();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadEmpresas() {
        controller.findAll(new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                EventBus.getDefault().post(new Success(empresas));
            }
        });
    }

    private void configListView() {
        empresas = new ArrayList<>();
        empresasListAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_1, empresas);
        empresasListView.setAdapter(empresasListAdapter);
        empresasListView.setOnItemClickListener((adapterView, view, position, l) -> onClickEmpresasListView(position));
    }

    private void onClickEmpresasListView(int position) {
        Intent intent = new Intent(this, EmpresaActivity.class);
        intent.putExtra("empresa", empresas.get(position));
        startActivity(intent);
    }

    public void onClickAdicionarButton(View view) {
        Empresa empresa = new Empresa();
        Intent intent = new Intent(this, EmpresaActivity.class);
        intent.putExtra("empresa", empresa);
        startActivity(intent);
    }

    private class Success {
        private final List<Empresa> empresas;
        public Success(List<Empresa> empresas) {
            this.empresas = empresas;
        }
    }

    @Subscribe
    public void on(Success success) {
        empresas.clear();
        empresas.addAll(success.empresas);
        empresasListAdapter.notifyDataSetChanged();
    }
}
