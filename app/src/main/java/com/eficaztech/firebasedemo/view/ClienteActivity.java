package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.model.Cliente;

public class ClienteActivity extends AppCompatActivity {

    private EditText cpfEditText;
    private EditText nomeEditText;
    private EditText emailEditText;

    private ClienteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        setComponents();

        setController();

        loadCliente();

    }

    private void setController() {
        controller = new ClienteController();
    }

    private void setComponents() {
        cpfEditText = findViewById(R.id.cpfEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);
    }

    private void loadCliente() {

        Cliente cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        cpfEditText.setText(cliente.getCpf());
        nomeEditText.setText(cliente.getNome());
        emailEditText.setText(cliente.getEmail());

    }

    public void onClickSalvarButton(View view) {

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfEditText.getText().toString());
        cliente.setNome(nomeEditText.getText().toString());
        cliente.setEmail(emailEditText.getText().toString());

        controller.cadastrar(cliente);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfEditText.getText().toString());

        controller.excluir(cliente);

        finish();

    }

}
