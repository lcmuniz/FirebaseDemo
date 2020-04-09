package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.ProdutoController;
import com.eficaztech.firebasedemo.model.Produto;

import java.util.UUID;

public class ProdutoActivity extends AppCompatActivity {

    private EditText codigoEditText;
    private EditText nomeEditText;
    private EditText precoEditText;

    private ProdutoController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        setComponents();
        setController();

        loadProduto();

    }

    private void setComponents() {
        codigoEditText = findViewById(R.id.codigoEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        precoEditText = findViewById(R.id.precoEditText);

    }

    private void setController() {
        controller = new ProdutoController();
    }

    private void loadProduto() {

        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        codigoEditText.setText(produto.getCodigo());
        nomeEditText.setText(produto.getNome());
        precoEditText.setText(produto.getPreco().toString());

    }

    public void onClickSalvarButton(View view) {

        Produto produto = new Produto();
        produto.setCodigo(codigoEditText.getText().toString());
        produto.setNome(nomeEditText.getText().toString());
        produto.setPreco(Double.valueOf(precoEditText.getText().toString()));

        controller.cadastrar(produto);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Produto produto = new Produto();
        produto.setCodigo(codigoEditText.getText().toString());

        controller.excluir(produto);

        finish();

    }
}
