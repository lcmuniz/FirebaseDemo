package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ClienteController;
import com.eficaztech.firebasedemo.model.Cliente;

public class ClienteActivity extends AppCompatActivity {

    EditText cpfEditText;
    EditText nomeEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        loadCliente();

    }

    private void loadCliente() {

        Cliente cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        if (cliente == null) {
            cliente = new Cliente();
        }

        cpfEditText = findViewById(R.id.cpfEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);

        cpfEditText.setText(cliente.getCpf());
        nomeEditText.setText(cliente.getNome());
        emailEditText.setText(cliente.getEmail());

    }

    public void onClickSalvarButton(View view) {

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfEditText.getText().toString());
        cliente.setNome(nomeEditText.getText().toString());
        cliente.setEmail(emailEditText.getText().toString());

        ClienteController controller = new ClienteController();
        controller.cadastrar(cliente);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfEditText.getText().toString());

        ClienteController controller = new ClienteController();
        controller.excluir(cliente);

        finish();

    }

}
