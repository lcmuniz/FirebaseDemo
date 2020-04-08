package com.eficaztech.firebasedemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eficaztech.firebasedemo.R;
import com.eficaztech.firebasedemo.controller.EmpresaController;
import com.eficaztech.firebasedemo.controller.ProdutoController;
import com.eficaztech.firebasedemo.model.Empresa;
import com.eficaztech.firebasedemo.model.Produto;

import java.util.UUID;

public class ProdutoActivity extends AppCompatActivity {

    EditText codigoEditText;
    EditText nomeEditText;
    EditText precoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        loadProduto();

    }

    private void loadProduto() {

        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        if (produto == null) {
            produto = new Produto();
            produto.setCodigo(UUID.randomUUID().toString());
            produto.setPreco(new Double(0));
        }

        codigoEditText = findViewById(R.id.codigoEditText);
        nomeEditText = findViewById(R.id.nomeEditText);
        precoEditText = findViewById(R.id.precoEditText);

        codigoEditText.setText(produto.getCodigo());
        nomeEditText.setText(produto.getNome());
        precoEditText.setText(produto.getPreco().toString());

    }

    public void onClickAdicionarButton(View view) {

        Produto produto = new Produto();
        produto.setCodigo(codigoEditText.getText().toString());
        produto.setNome(nomeEditText.getText().toString());
        produto.setPreco(Double.valueOf(precoEditText.getText().toString()));

        ProdutoController controller = new ProdutoController();
        controller.cadastrar(produto);

        finish();

    }

    public void onClickExcluirButton(View view) {

        Produto produto = new Produto();
        produto.setCodigo(codigoEditText.getText().toString());

        ProdutoController controller = new ProdutoController();
        controller.excluir(produto);

        finish();

    }
}
