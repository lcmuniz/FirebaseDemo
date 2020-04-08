package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.controller.FuncionarioController;
import com.eficaztech.firebasedemo.controller.IEmpresaListener;
import com.eficaztech.firebasedemo.model.Empresa;
import com.eficaztech.firebasedemo.model.Funcionario;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioActivity extends AppCompatActivity {

    private EditText cpfEditText;
    private EditText nomeEditText;
    private EditText emailEditText;
    private Spinner empresasSpinner;

    private EmpresaController empresaController;
    private FuncionarioController funcionarioController;

    private ArrayList<Empresa> empresas;
    private ArrayAdapter<Empresa> empresasListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        EventBus.getDefault().register(this);

        setComponents();

        setControllers();

        configSpinner();
        loadEmpresas();

    }

    private void setComponents() {
        cpfEditText = findViewById(R.id.cpfEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);
        empresasSpinner = findViewById(R.id.empresasSpinner);
    }

    private void setControllers() {
        empresaController = new EmpresaController();
        funcionarioController = new FuncionarioController();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadEmpresas() {
        empresaController.findAll(new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                EventBus.getDefault().post(new Success(empresas));
            }
        });
    }

    private void configSpinner() {
        empresas = new ArrayList<>();
        empresasListAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_1, empresas);
        empresasSpinner.setAdapter(empresasListAdapter);
    }

    private void loadFuncionario() {

        Funcionario funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        if (funcionario == null) {
            funcionario = new Funcionario();
        }

        cpfEditText.setText(funcionario.getCpf());
        nomeEditText.setText(funcionario.getNome());
        emailEditText.setText(funcionario.getEmail());
        int position = empresas.indexOf(funcionario.getEmpresa());
        empresasSpinner.setSelection(position);

    }

    public void onClickSalvarButton(View view) {

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(cpfEditText.getText().toString());
        funcionario.setNome(nomeEditText.getText().toString());
        funcionario.setEmail(emailEditText.getText().toString());
        funcionario.setEmpresa(empresas.get(empresasSpinner.getSelectedItemPosition()));

        funcionarioController.cadastrar(funcionario);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(cpfEditText.getText().toString());

        funcionarioController.excluir(funcionario);

        finish();

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
        loadFuncionario();
    }
}
