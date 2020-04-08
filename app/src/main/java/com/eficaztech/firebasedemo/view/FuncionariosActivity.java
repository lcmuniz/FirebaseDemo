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

    ArrayList<Funcionario> funcionarios;
    ArrayAdapter<Funcionario> funcionariosListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        EventBus.getDefault().register(this);

        configListView();
        loadFuncionarios();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadFuncionarios() {
        FuncionarioController controller = new FuncionarioController();
        controller.findAll(new IFuncionarioListener() {
            @Override
            public void onSuccess(List<Funcionario> funcionarios) {
                EventBus.getDefault().post(new Success(funcionarios));
            }
        });
    }

    private void configListView() {
        ListView funcionariosListView = findViewById(R.id.funcionariosListView);
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
        startActivity(new Intent(this, FuncionarioActivity.class));
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
