package com.eficaztech.firebasedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.FuncionarioController;
import com.eficaztech.firebasedemo.controller.IFuncionarioListener;
import com.eficaztech.firebasedemo.model.Funcionario;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class FuncionariosActivity extends AppCompatActivity {

    private ListView funcionariosListView;

    private FuncionarioController controller;

    private ArrayList<Funcionario> funcionarios;
    private ArrayAdapter<Funcionario> funcionariosListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        EventBus.getDefault().register(this);

        setComponents();
        setController();

        configListView();
        loadFuncionarios();

    }

    private void setComponents() {
        funcionariosListView = findViewById(R.id.funcionariosListView);
    }

    private void setController() {
        controller = new FuncionarioController();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadFuncionarios() {
        controller.findAll(new IFuncionarioListener() {
            @Override
            public void onSuccess(List<Funcionario> funcionarios) {
                EventBus.getDefault().post(new Success(funcionarios));
            }
        });
    }

    private void configListView() {
        funcionarios = new ArrayList<>();
        funcionariosListAdapter = new ArrayAdapter<Funcionario>(this, android.R.layout.simple_list_item_1, funcionarios);
        funcionariosListView.setAdapter(funcionariosListAdapter);
        funcionariosListView.setOnItemClickListener((adapterView, view, position, l) -> onClickFuncionariosListView(position));
    }

    private void onClickFuncionariosListView(int position) {
        Intent intent = new Intent(this, FuncionarioActivity.class);
        intent.putExtra("funcionario", funcionarios.get(position));
        startActivity(intent);
    }

    public void onClickAdicionarButton(View view) {
        Funcionario funcionario = new Funcionario();
        Intent intent = new Intent(this, FuncionarioActivity.class);
        intent.putExtra("funcionario", funcionario);
        startActivity(intent);
    }

    private class Success {
        private final List<Funcionario> funcionarios;

        public Success(List<Funcionario> funcionarios) {
            this.funcionarios = funcionarios;
        }
    }

    @Subscribe
    public void on(Success success) {
        funcionarios.clear();
        funcionarios.addAll(success.funcionarios);
        funcionariosListAdapter.notifyDataSetChanged();
    }
}
