package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.model.Empresa;

public class EmpresaActivity extends AppCompatActivity {

    private EditText cnpjEditText;
    private EditText nomeEditText;
    private EditText enderecoEditText;

    private EmpresaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        setComponents();
        setController();

        loadEmpresa();

    }

    private void setComponents() {
        cnpjEditText = findViewById(R.id.cnpjEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        enderecoEditText = findViewById(R.id.enderecoEditText);
    }

    private void setController() {
        controller = new EmpresaController();
    }

    private void loadEmpresa() {

        Empresa empresa = (Empresa) getIntent().getSerializableExtra("empresa");

        if (empresa == null) {
            empresa = new Empresa();
        }

        cnpjEditText.setText(empresa.getCnpj());
        nomeEditText.setText(empresa.getNome());
        enderecoEditText.setText(empresa.getEndereco());

    }

    public void onClickSalvarButton(View view) {

        Empresa empresa = new Empresa();
        empresa.setCnpj(cnpjEditText.getText().toString());
        empresa.setNome(nomeEditText.getText().toString());
        empresa.setEndereco(enderecoEditText.getText().toString());

        controller.cadastrar(empresa);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Empresa empresa = new Empresa();
        empresa.setCnpj(cnpjEditText.getText().toString());

        controller.excluir(empresa);

        finish();

    }
}
