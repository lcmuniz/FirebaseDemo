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

    EditText cpfEditText;
    EditText nomeEditText;
    EditText emailEditText;
    Spinner empresasSpinner;

    private ArrayList<Empresa> empresas;
    private ArrayAdapter<Empresa> empresasListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        EventBus.getDefault().register(this);

        configSpinner();
        loadEmpresas();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loadEmpresas() {
        EmpresaController controller = new EmpresaController();
        controller.findAll(new IEmpresaListener() {
            @Override
            public void onSuccess(List<Empresa> empresas) {
                EventBus.getDefault().post(new Success(empresas));
            }
        });
    }

    private void configSpinner() {
        Spinner empresasSpinner = findViewById(R.id.empresasSpinner);
        empresas = new ArrayList<>();
        empresasListAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_1, empresas);
        empresasSpinner.setAdapter(empresasListAdapter);
        //empresasListView.setOnItemClickListener((adapterView, view, position, l) -> onClickEmpresasListView(position));
    }

    private void loadFuncionario() {

        Funcionario funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        if (funcionario == null) {
            funcionario = new Funcionario();
        }

        cpfEditText = findViewById(R.id.cpfEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);
        empresasSpinner = findViewById(R.id.empresasSpinner);

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
        FuncionarioController controller = new FuncionarioController();
        controller.cadastrar(funcionario);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(cpfEditText.getText().toString());

        FuncionarioController controller = new FuncionarioController();
        controller.excluir(funcionario);

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
